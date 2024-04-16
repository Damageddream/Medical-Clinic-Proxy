package com.damageddream.MedicalClinicProxy.service.advice;

import com.damageddream.MedicalClinicProxy.exception.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
// dodać exception handler do feign clienta
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class ServiceExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(
            NotFoundException notFoundException) {
        log.error("NotFoundException: {}", notFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                notFoundException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(
            ForbiddenException forbiddenException) {
        log.error("ForbiddenException: {}", forbiddenException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN,
                forbiddenException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> conflictExceptionHandler(
            ConflictException conflictException) {
        log.error("ConflictException: {}", conflictException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT,
                conflictException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> internalServerExceptionHandler(
            InternalServerErrorException internalServerErrorException) {
        log.error("InternalServerErrorException: {}", internalServerErrorException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                internalServerErrorException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> serviceUnavailableExceptionHandler(
            ServiceUnavailableException serviceUnavailableException) {
        log.error("ServiceUnavailableException: {}", serviceUnavailableException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE,
                serviceUnavailableException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }
}
