package com.aes.corebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private String message;
    private boolean success;
    private Object data;

    public APIResponse setResponses(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
        return this;
    }

    public static ResponseEntity<?> getResponse(String message, boolean success, Object data, HttpStatus status) {
        return new ResponseEntity(new APIResponse(message, success, data), status);
    }


}


