package com.gbsb.api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Builder
@Getter
public class ExceptionResponse {
    private  LocalDateTime timeStamp;
    private  int status;
    private  String error;
    private  String message;
    private  String path;
}
