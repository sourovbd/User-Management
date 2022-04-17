package com.sv.corebackend.service.usermanagement;

import com.sv.corebackend.entity.UserCredential;

public interface EmailSender {

    void send(String to, String email);
    String buildEmailText(UserCredential userCredential);
}
