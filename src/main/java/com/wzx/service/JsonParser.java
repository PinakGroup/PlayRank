package com.wzx.service;

import com.wzx.domain.Json.*;
import com.wzx.exception.Location;
import com.wzx.exception.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by arthurwang on 17/3/24.
 */
@Service
public class JsonParser {
    private static final int MAX_NESTING_LEVEL = 1000;
    private static final int MIN_BUFFER_SIZE = 10;
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    private Reader reader;
    private char[] buffer;
    private int bufferOffset;
    private int index;
    private int fill;
    private int line;
    private int lineOffset;
    private int current;
    private StringBuilder captureBuffer;    // 需要存储的字符串
    private int captureStart;
    private int nestingLevel;

    private JsonArray array;
    private JsonObject j = new JsonObject();   // 存储一个Object

    /*
 * |                      bufferOffset
 *                        v
 * [a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t]        < input
 *                       [l|m|n|o|p|q|r|s|t|?|?]    < buffer
 *                          ^               ^
 *                       |  index           fill
 */

    public void parse(String string) {
        if (string == null) {
            throw new NullPointerException("string is null");
        }
        int bufferSize = Math.max(MIN_BUFFER_SIZE, Math.min(DEFAULT_BUFFER_SIZE, string.length()));
        try {
            parse(new StringReader(string), bufferSize);
        } catch (IOException exception) {
            // StringReader does not throw IOException
            throw new RuntimeException(exception);
        }
    }

    public void parse(Reader reader) throws IOException {
        parse(reader, DEFAULT_BUFFER_SIZE);
    }

    public void parse(Reader reader, int bufferSize) throws IOException {
        if (reader == null) {
            throw new NullPointerException("reader is null");
        }
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize is zero or negative");
        }
        this.reader = reader;
        buffer = new char[bufferSize];
        bufferOffset = 0;   // 从输入起始位置开始读入buffer
        index = 0;  // buffer处理从起始位置开始
        fill = 0;   // buffer内容长度初始为0
        line = 1;   // 第一行
        lineOffset = 0;
        current = 0;    // 当前字符
        captureStart = -1;  // 没有开始捕获字符串的状态
        read(); // 读一个字符
        skipWhiteSpace();   // 如果读到空白，继续读下去
        readValue();    // 开始读取JSON值
        skipWhiteSpace();   // 值结束后的空白
        if (!isEndOfText()) {
            throw error("Unexpected character");
        }
    }

    private void skipWhiteSpace() throws IOException {
        while (isWhiteSpace()) {
            read();
        }
    }

    private boolean isWhiteSpace() {
        return current == ' ' || current == '\t' || current == '\n' || current == '\r';
    }

    private boolean isEndOfText() {
        return current == -1;
    }

    private JsonValue readValue() throws IOException {
        JsonValue value = new JsonString("");
        switch (current) {
            case 'n':
                value = readNull();
                break;
            case 't':
                value = readTrue();
                break;
            case 'f':
                value = readFalse();
                break;
            case '"':
                value = readString();
                break;
            case '[':
                value = readArray();
                break;
            case '{':
                readObject();
                break;
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                readNumber();
                break;
            default:
                throw expected("value");
        }
        return value;
    }

    private JsonLiteral readNull() throws IOException {
        read();
        readRequiredChar('u');
        readRequiredChar('l');
        readRequiredChar('l');
        return new JsonLiteral("null");
    }

    private JsonLiteral readTrue() throws IOException {
        read();
        readRequiredChar('r');
        readRequiredChar('u');
        readRequiredChar('e');
        return new JsonLiteral("true");
    }

    private JsonLiteral readFalse() throws IOException {
        read();
        readRequiredChar('a');
        readRequiredChar('l');
        readRequiredChar('s');
        readRequiredChar('e');
        return new JsonLiteral("false");
    }

    private void readRequiredChar(char ch) throws IOException {
        if (!readChar(ch)) {
            throw expected("'" + ch + "'");
        }
    }

    private boolean readChar(char ch) throws IOException {
        if(current != ch) {
            return false;
        }
        read();
        return true;
    }

    private JsonString readString() throws IOException {
        read();
        startCapture();
        while (current != '"') {
            if (current == '\\') {
                pauseCapture();
                readEscape();
                startCapture();
            } else if (current < 0x20) {
                throw expected("valid string character");
            } else {
                read();
            }
        }
        String string = endCapture();
        read();
        return new JsonString(string);
    }

    private void startCapture() {
        if (captureBuffer == null) {
            captureBuffer = new StringBuilder();
        }
        captureStart = index - 1;
    }

    private void pauseCapture() {
        int end = current == -1 ? index : index - 1;
        captureBuffer.append(buffer, captureStart, end - captureStart);
        captureStart = -1;
    }

    private String endCapture() {
        int start = captureStart;
        int end = index - 1;
        captureStart = -1;
        if (captureBuffer.length() > 0) {
            captureBuffer.append(buffer, start, end - start);
            String captured = captureBuffer.toString();
            captureBuffer.setLength(0);
            return captured;
        }
        return new String(buffer, start, end - start);
    }

    // 处理转义字符
    private void readEscape() throws IOException {
        read();
        switch (current) {
            case '"':
            case '/':
            case '\\':
                captureBuffer.append((char)current);
                break;
            case 'b':
                captureBuffer.append('\b');
                break;
            case 'f':
                captureBuffer.append('\f');
                break;
            case 'n':
                captureBuffer.append('\n');
                break;
            case 'r':
                captureBuffer.append('\r');
                break;
            case 't':
                captureBuffer.append('\t');
                break;
            case 'u':
                char[] hexChars = new char[4];
                for (int i = 0; i < 4; i++) {
                    read();
                    if (!isHexDigit()) {
                        throw expected("hexadecimal digit");
                    }
                    hexChars[i] = (char)current;
                }
                captureBuffer.append((char)Integer.parseInt(new String(hexChars), 16));
                break;
            default:
                throw expected("valid escape sequence");
        }
        read();
    }

    private boolean isHexDigit() {
        return current >= '0' && current <= '9'
                || current >= 'a' && current <= 'f'
                || current >= 'A' && current <= 'F';
    }

    private JsonValue readArray() throws IOException {
        JsonArray t = new JsonArray();
        read();
        if (++nestingLevel > MAX_NESTING_LEVEL) {
            throw error("Nesting too deep");
        }
        skipWhiteSpace();
        // 有逗号说明有一组(List)值，要一直读完
        do {
            skipWhiteSpace();
            JsonValue value = readValue();
            t.add(value);
            skipWhiteSpace();
        } while (readChar(','));
        if (!readChar(']')) {
            throw expected("',' or ']'");
        }
        nestingLevel--;
        if (nestingLevel == 1) {
            array.add(t);
        }
        return t;
    }

    private void readObject() throws IOException {
        read();
        if (++nestingLevel > MAX_NESTING_LEVEL) {
            throw error("Nesting too deep");
        }
        skipWhiteSpace();
        if (readChar('}')) {
            nestingLevel--;
            return;
        }
        do {
            array = new JsonArray();
            skipWhiteSpace();
            JsonString key = readName();
            skipWhiteSpace();
            if (!readChar(':')) {
                throw expected("':'");
            }
            skipWhiteSpace();
            JsonValue value = readValue();
            if (!value.isArray()) {
                array.add(value);
            }
            skipWhiteSpace();
            if (array.size() == 1) {
                j.setObject(key.toString(), array.getLastItem());
            } else j.setObject(key.toString(), array);
        } while (readChar(','));
        if (!readChar('}')) {
            throw expected("',' or '}'");
        }
        nestingLevel--;
    }

    private JsonString readName() throws IOException {
        if (current != '"') {
            throw expected("name");
        }
        return readString();
    }

    private void readNumber() throws IOException {
        startCapture();
        readChar('-');
        int firstDigit = current;
        if (!readDigit()) {
            throw expected("digit");
        }
        if (firstDigit != '0') {
            while (readDigit()) {
            }
        }
        readFraction();
        readExponent();
        //temp.add(Double.parseDouble(endCapture()));
    }

    private boolean readDigit() throws IOException {
        if (!isDigit()) {
            return false;
        }
        read();
        return true;
    }

    private boolean isDigit() {
        return current >= '0' && current <= '9';
    }

    // 读小数
    private boolean readFraction() throws IOException {
        if (!readChar('.')) {
            return false;
        }
        if (!readDigit()) {
            throw expected("digit");
        }
        while (readDigit()) {
        }
        return true;
    }

    private boolean readExponent() throws IOException {
        if (!readChar('e') && !readChar('E')) {
            return false;
        }
        if (!readChar('+')) {
            readChar('-');
        }
        if (!readDigit()) {
            throw expected("digit");
        }
        while (readDigit()) {
        }
        return true;
    }

    private Location getLocation() {
        int offset = bufferOffset + index - 1;
        int column = offset - lineOffset + 1;
        return new Location(offset, line, column);
    }

    private ParseException expected(String expected) {
        if (isEndOfText()) {
            return error("Unexpected end of input");
        }
        return error("Expected " + expected);
    }

    private ParseException error(String message) {
        return new ParseException(message, getLocation());
    }

    public JsonObject getJ() {
        return j;
    }

    private void read() throws IOException {
        if (index == fill) {
            if (captureStart != -1) {
                captureBuffer.append(buffer, captureStart, fill - captureStart);
                captureStart = 0;
            }
            bufferOffset += fill;   // 源字符串读完了fill这么多，bufferOffset向后移动
            fill = reader.read(buffer, 0, buffer.length);   // 向buffer里读满length长度的字符
            index = 0;  // buffer起始设置为0
            // read()遇到文档结束返回-1，current设置-1
            if (fill == -1) {
                current = -1;
                index++;
                return;
            }
        }
        if (current == '\n') {
            line++;
            lineOffset = bufferOffset + index;
        }
        current = buffer[index++];  // 目前正在处理的字符挪到buffer里的下一个
    }
}
