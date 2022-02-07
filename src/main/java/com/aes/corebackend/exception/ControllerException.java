package com.aes.corebackend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;

    private String errorMessage;
}
