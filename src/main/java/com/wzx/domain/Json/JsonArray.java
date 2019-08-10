package com.wzx.domain.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arthurwang on 17/3/25.
 */
public class JsonArray extends JsonValue implements Iterable<JsonValue>{
    private final List<JsonValue> values;

    public JsonArray() {
        values = new ArrayList<>();
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public void add(JsonValue value) {
        values.add(value);
    }

    public JsonValue getLastItem() {
        return values.get(values.size() - 1);
    }

    public int size() {
        return values.size();
    }
    public Iterator<JsonValue> iterator() {
        final Iterator<JsonValue> iterator = values.iterator();
        return new Iterator<JsonValue>() {

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public JsonValue next() {
                return iterator.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        return Arrays.toString(values.toArray());
    }
}
