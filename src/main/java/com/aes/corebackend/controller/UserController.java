package com.aes.corebackend.controller;

import com.aes.corebackend.service.UserCredentialService;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialService userCredentialService;

    @GetMapping("user/save/credential")
    public String hello() {

        return "Hello AES";
    }


}
