package com.chessd.chess.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();


        StackTraceElement stackTraceElement = ex.getStackTrace()[0];

        errorDetails.put("error", ex.getMessage());
        errorDetails.put("exception", ex.getClass().getSimpleName());
        errorDetails.put("class", stackTraceElement.getClassName());
        errorDetails.put("method", stackTraceElement.getMethodName());
        errorDetails.put("line", stackTraceElement.getLineNumber());
        errorDetails.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
