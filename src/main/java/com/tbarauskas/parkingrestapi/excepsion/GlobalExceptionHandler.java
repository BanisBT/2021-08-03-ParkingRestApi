package com.tbarauskas.parkingrestapi.excepsion;

import com.tbarauskas.parkingrestapi.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> exceptionHandle(ResourceNotFoundException e) {
        log.debug("Resource with id - {} was not found", e.getId());
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(),
                String.format("Resource with id %d - was not found", e.getId())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> exceptionHandle(MethodArgumentNotValidException e) {
        log.debug("Invalid argument added to field. Message = {}", e.getMessage());
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Invalid body argument"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordStatusNotFoundException.class)
    public ResponseEntity<Error> exceptionHandle(RecordStatusNotFoundException e) {
        log.error("Record status not found - {}", e.getName());
        return new ResponseEntity<>(new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandle(Exception e) {
        log.error("Unexpected error - {}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
