package com.wzx.exception;

/**
 * Created by arthurwang on 17/3/25.
 */
public class ParseException extends RuntimeException {
    private final Location location;

    public ParseException(String message, Location location) {
        super(message + " at " + location);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }


}
