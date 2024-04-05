package com.damageddream.MedicalClinicProxy.service.advice;

import com.damageddream.MedicalClinicProxy.exception.PatientNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@NoArgsConstructor
public class ServiceExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> patientNotFoundExceptionHandler(
            PatientNotFoundException patientNotFoundException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                patientNotFoundException.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
