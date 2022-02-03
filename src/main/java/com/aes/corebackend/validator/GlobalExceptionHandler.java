package com.aes.corebackend.validator;

import com.aes.corebackend.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException me) {

        List<FieldError> fieldErrors = me
                .getBindingResult()
                .getFieldErrors();

        List<FieldErrorMessage> fieldErrorMessages = fieldErrors
                .stream()
                .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(fieldErrorMessages, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationExceptions(ValidationException ve) {

        return new ResponseEntity<>(new ErrorMessage("400", ve.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
