package com.gbsb.api.exception;

public class CrawlException extends RuntimeException {

    public CrawlException(String message) {
        super(message);
    }

    public CrawlException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlException(Exception e) {
        super(e);
    }
}
