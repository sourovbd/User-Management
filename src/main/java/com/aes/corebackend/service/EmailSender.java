package com.aes.corebackend.service;

public interface EmailSender {
    void send(String to, String email);
}
