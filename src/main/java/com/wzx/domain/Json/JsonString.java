package com.wzx.domain.Json;

/**
 * Created by arthurwang on 17/3/25.
 */
public class JsonString extends JsonValue {
    private final String string;

    public JsonString(String string) {
        if (string == null) {
            throw new NullPointerException("string is null");
        }
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
