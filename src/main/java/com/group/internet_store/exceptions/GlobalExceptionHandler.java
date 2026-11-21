package com.group.internet_store.exceptions;

import com.group.internet_store.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private GenericConstructor genericConstructor;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<Map<String,String>> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return genericConstructor.construct(
                ex.getClass().getName(), errors,HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ProductsListIsEmpty.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handle(ProductsListIsEmpty ex, HttpServletRequest request) {
        return genericConstructor.construct(
                ex.getClass().getName(),ex.getMessage(),HttpStatus.NOT_FOUND, request
        );
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handle(ProductNotFound ex, HttpServletRequest request) {
        return genericConstructor.construct(
                ex.getClass().getName(),ex.getMessage(),HttpStatus.NOT_FOUND, request
        );
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handle(UserNotFound ex, HttpServletRequest request) {
        return genericConstructor.construct(
                ex.getClass().getName(),ex.getMessage(),HttpStatus.NOT_FOUND, request
        );
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> handle(UsernameAlreadyExists ex, HttpServletRequest request) {
        return genericConstructor.construct(
                ex.getClass().getName(),ex.getMessage(),HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(MustBeGreaterThanZero.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> handle(MustBeGreaterThanZero ex, HttpServletRequest request){
        return genericConstructor.construct(
                ex.getClass().getName(),ex.getMessage(),HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(CartNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handle(CartNotFound ex, HttpServletRequest request){
        return genericConstructor.construct(
                ex.getClass().getName(),ex.getMessage(),HttpStatus.NOT_FOUND,request
        );
    }
}
