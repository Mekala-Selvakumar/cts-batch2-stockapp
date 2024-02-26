package com.stackroute.stockapp.exceptions;

/*
 * Use  @ControlAdivce to handle the exceptions globally
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{
    @ExceptionHandler(value = StockNotFoundException.class)
    public ResponseEntity<Object> exception(StockNotFoundException exception) {
       
        return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = StockAlreadyExistException.class)
    public ResponseEntity<Object> exception(StockAlreadyExistException exception) {
        return new ResponseEntity<>("Stock already exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<Object> exception(UserAlreadyExistException exception) {
        return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
    }
}
