package com.wzx.domain.Json;

import java.io.Serializable;

/**
 * Created by arthurwang on 17/3/25.
 */
public abstract class JsonValue implements Serializable {
    JsonValue() {
    }

    public boolean isObject() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isTrue() {
        return false;
    }

    public boolean isFalse() {
        return false;
    }

    public boolean isNull() {
        return false;
    }
}
