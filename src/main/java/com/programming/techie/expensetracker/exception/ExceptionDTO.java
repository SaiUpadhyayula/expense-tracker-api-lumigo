package com.programming.techie.expensetracker.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ExceptionDTO(String message,
                           String path,
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                           LocalDateTime timeStamp) {

    public ExceptionDTO(String message, String path) {
        this(message, path, LocalDateTime.now());
    }
}
