package com.trivago.hotelmanagement.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.trivago.hotelmanagement.model.Error;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResponseExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException exception) {
        LOGGER.error(exception.getMessage());
        List<Error> errors = new ArrayList<>();
        exception.getFieldErrors().forEach(fieldError -> {
            errors.add(new Error(fieldError.getDefaultMessage(), fieldError.getField()));
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) throws IOException
    {
        if (exception.getCause() instanceof InvalidFormatException) {
            String errorMessage = String.format("Bad Value: {%s} for the given Field: {%s}",  ((InvalidFormatException)exception.getCause()).getValue(), ((InvalidFormatException)exception.getCause()).getTargetType().getSimpleName());
            LOGGER.error(errorMessage);
            return ResponseEntity
                    .badRequest()
                    .body(new Error(errorMessage, ((InvalidFormatException)exception.getCause()).getTargetType().getSimpleName()));
        }
        return ResponseEntity
                .badRequest()
                .body(new Error(exception.getMessage(), null));
    }


}
