package com.gbsb.api.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException() {
        super("Tag not found");
    }

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(String field, String value) {
        super("Tag not found with " + field + ": " + value);
    }
}
