package com.aes.corebackend.service;

import com.aes.corebackend.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCredentialService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;
}
