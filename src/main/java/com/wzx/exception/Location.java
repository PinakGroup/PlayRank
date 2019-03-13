package com.wzx.exception;

/**
 * Created by arthurwang on 17/3/25.
 */

/**
 * An immutable object that represents a location in the parsed text.
 */
public class Location {
    /**
     * The absolute character index, starting at 0.
     */
    private final int offset;

    /**
     * The line number, starting at 1.
     */
    private final int line;

    /**
     * The column number, starting at 1.
     */
    private final int column;

    public Location(int offset, int line, int column) {
        this.offset = offset;
        this.column = column;
        this.line = line;
    }

    @Override
    public String toString() {
        return line + " : " + column;
    }

    @Override
    public int hashCode() {
        return offset;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Location other = (Location)obj;
        return offset == other.offset && column == other.column && line == other.line;
    }
}
