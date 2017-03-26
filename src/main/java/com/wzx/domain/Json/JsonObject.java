package com.wzx.domain.Json;

import java.util.HashMap;

/**
 * Created by arthurwang on 17/3/24.
 */
public class JsonObject extends JsonValue {
    private HashMap<String, Object> list = new HashMap<>();

    public void setObject(String key, Object value) {
        list.put(key, value);
    }
    public String getObject() {
        return list.toString();
    }

    @Override
    public boolean isObject() {
        return true;
    }
}
