package com.wzx.domain;

import java.util.HashMap;

/**
 * Created by arthurwang on 17/3/24.
 */
public class JsonObject {
    private HashMap<String, Object> list = new HashMap<>();

    public void setKV(String key, Object value) {
        list.put(key, value);
    }
    public String getList() {
        return list.toString();
    }
}
