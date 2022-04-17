package com.sv.corebackend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String s) {

        super(s);
    }
}
