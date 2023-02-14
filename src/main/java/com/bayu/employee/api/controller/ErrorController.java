package com.bayu.employee.api.controller;

import com.bayu.employee.api.exception.BadRequestException;
import com.bayu.employee.api.exception.ResourceAlreadyExistsException;
import com.bayu.employee.api.exception.ResourceNotFoundException;
import com.bayu.employee.api.exception.UnauthorizedException;
import com.bayu.employee.api.payload.ApiResponse;
import com.bayu.employee.api.payload.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, "Bad Request Validation", errors), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<MessageResponse> badRequestException(BadRequestException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MessageResponse> unauthorizedException(UnauthorizedException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<MessageResponse> conflictException(ResourceAlreadyExistsException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.CONFLICT);
    }

}
