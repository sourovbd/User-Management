package com.aes.corebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private String message;
    private boolean success;
    private Object data;

    public void setResponses(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }
}


