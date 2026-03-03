package com.gbsb.api.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("Post not found");
    }

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(Long id) {
        super("Post not found with id: " + id);
    }
}
