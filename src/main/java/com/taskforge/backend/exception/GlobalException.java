package com.taskforge.backend.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidations(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrity(DataIntegrityViolationException di){
        Map<String, String> errors = new HashMap<>();

        errors.put("error","Data Integrity Violation:" +di.getRootCause().getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> handleHttpMessageNotReadable(HttpMessageNotReadableException hm){
      Map<String,String> errors = new HashMap<>();

      errors.put("error","HttpMessageNotReadable:" + hm.getMostSpecificCause().getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolation(ConstraintViolationException cv){
        Map<String,String> map = new HashMap<>();

        cv.getConstraintViolations().forEach(cov -> {
            map.put(cov.getPropertyPath().toString(), cov.getMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

}
