package com.example.pagerduty.exception;

import com.example.pagerduty.dto.ErrorResponseDto;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedException ex) {
        logger.warn("UnauthorizedException: {}", ex.getMessage());
        ErrorResponseDto body = new ErrorResponseDto();
        body.setTimestamp(Instant.now());
        body.setStatus(HttpStatus.UNAUTHORIZED.value());
        body.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        body.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ErrorResponseDto> handleTooManyRequests(TooManyRequestsException ex) {
        logger.warn("TooManyRequestsException: {}", ex.getMessage());
        ErrorResponseDto body = new ErrorResponseDto();
        body.setTimestamp(Instant.now());
        body.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        body.setError(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
        body.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        logger.error("Exception: ", ex);
        ErrorResponseDto body = new ErrorResponseDto();
        body.setTimestamp(Instant.now());
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.setMessage("Internal error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
