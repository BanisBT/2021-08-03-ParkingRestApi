package com.tbarauskas.parkingrestapi.exceptsion;

import com.tbarauskas.parkingrestapi.model.Error;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Api(tags = "Exception controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> exceptionHandle(ResourceNotFoundException e) {
        log.debug("Resource with id - {} was not found", e.getId());
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(),
                String.format("Resource with id %d - was not found", e.getId())), HttpStatus.NOT_FOUND);
    }
//TODO koks statusas?
    @ExceptionHandler(NotEnoughMoneyToPayForParkingRecordException.class)
    public ResponseEntity<Error> exceptionHandle(NotEnoughMoneyToPayForParkingRecordException e) {
        log.debug("Not enough money to pay for parking ticket, user - {}", e.getUser());
        return new ResponseEntity<>(new Error(HttpStatus.INSUFFICIENT_STORAGE.value(),
                String.format("Insufficient funds, your balance after paying ticket would be %s", e.getBalance())),
                HttpStatus.INSUFFICIENT_STORAGE);
    }

//    TODO patikrinti ar grista ticket'as
    @ExceptionHandler(UserHasOpenTicketException.class)
    public ResponseEntity<Error> exceptionHandle(UserHasOpenTicketException e) {
        return new ResponseEntity<>(new Error(HttpStatus.FORBIDDEN.value(), "You have open ticket"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Error> exceptionHandle(InvalidArgumentException e) {
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Must be not null"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> exceptionHandle(MethodArgumentNotValidException e) {
        log.debug("Invalid argument added to field. Message = {}", e.getMessage());
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Invalid body argument"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Error> exceptionHandle(UsernameAlreadyExistException e) {
        log.debug("Username - {} already exist in db", e.getUsername());
        return new ResponseEntity<>(new Error(HttpStatus.CONFLICT.value(),
                String.format("Username - %s already exist", e.getUsername())),
                HttpStatus.CONFLICT);

    }

//    TODO HttpStatusas?
    @ExceptionHandler(ParkingCityNotFoundException.class)
    public ResponseEntity<Error> exceptionHandle(ParkingCityNotFoundException e) {
        log.warn("Parking city - {} not found in data base, or bad request", e.getName());
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Invalid parking city"),
                HttpStatus.BAD_REQUEST);
    }

//    TODO HttpStatus?
    @ExceptionHandler(ParkingRecordHasNotUserException.class)
    public ResponseEntity<Error> exceptionHandle(ParkingRecordHasNotUserException e) {
        log.warn("Parking record - {} don't has user", e.getId());
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), "Parking record don't has user"),
                HttpStatus.NOT_FOUND);
    }

    //    TODO HttpStatusas?
    @ExceptionHandler(ParkingZoneNotFoundException.class)
    public ResponseEntity<Error> exceptionHandle(ParkingZoneNotFoundException e) {
        log.warn("Parking zone - {} not found in data base, or bad request", e.getName());
        return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST.value(), "Invalid parking zone"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Error> exceptionHandle(AccessDeniedException e) {
        log.warn("Unauthorized attempt. Message - {}", e.getMessage());
        return new ResponseEntity<>(new Error(HttpStatus.FORBIDDEN.value(), "Access is forbidden"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppParametersInDateBaseNotFoundException.class)
    public ResponseEntity<Error> exceptionHandle(AppParametersInDateBaseNotFoundException e) {
        log.error("App parameter - {} in db not found", e.getName());
        e.printStackTrace();
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
