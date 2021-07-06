package com.base.java.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        String message = "Internal Server Error";
        List<String> detais = new ArrayList<>();
        detais.add(ex.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", message, detais);

        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceErrorException.class)
    public final ResponseEntity<Object> handleServiceExceptions(ServiceErrorException ex) {
        String message = "Service Error";
        List<String> detais = new ArrayList<>();
        detais.add(ex.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse(UNPROCESSABLE_ENTITY.value(), "UNPROCESSABLE_ENTITY",
                message, detais);

        return new ResponseEntity<>(error, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
        String message = "Internal Server Error";
        List<String> detais = new ArrayList<>();
        detais.add(ex.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse(NOT_FOUND.value(), "NOT_FOUND", message, detais);

        return new ResponseEntity(error, NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedErrorException.class)
    public final ResponseEntity<Object> handleUnauthorizedExceptions(UnauthorizedErrorException ex) {
        String message = "Usuário não autorizado";
        List<String> detais = new ArrayList<>();
        detais.add(ex.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse(UNAUTHORIZED.value(), "UNAUTHORIZED",
                message, detais);

        return new ResponseEntity<>(error, UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers, HttpStatus status,
                                                          WebRequest request) {
        String message = "Validation Failed";

        List<String> details = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(BAD_REQUEST.value(), "BAD_REQUEST", message, details);

        return new ResponseEntity(error, BAD_REQUEST);
    }

}
