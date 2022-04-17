package com.sv.corebackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorMessage {

    private String status;
    private String message;

}
