package com.aes.corebackend.service;

import com.aes.corebackend.entity.UserCredential;

public interface EmailSender {

    void send(String to, String email);
    String buildEmailText(UserCredential userCredential);
}
