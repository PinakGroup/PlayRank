package com.wzx.domain.Json;

/**
 * Created by arthurwang on 17/3/25.
 */
public class JsonLiteral extends JsonValue {
    private final String value;
    private final boolean isNull;
    private final boolean isTrue;
    private final boolean isFalse;

    public JsonLiteral(String value) {
        this.value = value;
        isNull = "null".equals(value);
        isTrue = "true".equals(value);
        isFalse = "false".equals(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean isNull() {
        return isNull;
    }

    @Override
    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public boolean isFalse() {
        return isFalse;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        JsonLiteral other = (JsonLiteral)object;
        return value.equals(other.value);
    }
}
