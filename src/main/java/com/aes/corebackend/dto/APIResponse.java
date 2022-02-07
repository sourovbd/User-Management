package com.aes.corebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private String message;
    private boolean success;
    private Object data;

   public APIResponse setResponse(String message, boolean success, Object data) {
       this.message = message;
       this.success = success;
       this.data = data;
       return this;
   }

   public static APIResponse getApiResponse() {
       return new APIResponse();
   }

}


