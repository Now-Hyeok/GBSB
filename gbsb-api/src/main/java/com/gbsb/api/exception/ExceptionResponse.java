package com.gbsb.api.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private  LocalDateTime timeStamp;
    private  int status;
    private  String error;
    private  String message;
    private  String path;
}
