package com.aes.corebackend.validator;

import com.aes.corebackend.util.*;
import com.aes.corebackend.util.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException me) {

        APIResponse response = APIResponse.error();
        for (FieldError fe : me.getFieldErrors()) {
            response.addFieldError(fe.getField(), fe.getDefaultMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationExceptions(ValidationException ve) {

        return new ResponseEntity<>(new ErrorMessage("400", ve.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
