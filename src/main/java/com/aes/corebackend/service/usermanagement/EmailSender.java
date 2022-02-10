package com.aes.corebackend.service.usermanagement;

import com.aes.corebackend.entity.usermanagement.UserCredential;

public interface EmailSender {

    void send(String to, String email);
    String buildEmailText(UserCredential userCredential);
}
