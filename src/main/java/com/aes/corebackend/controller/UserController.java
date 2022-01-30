package com.aes.corebackend.controller;

import com.aes.corebackend.dto.personnelmanagement.PersonalBasicInfoDTO;
import com.aes.corebackend.dto.personnelmanagement.PersonnelManagementResponseDTO;
import com.aes.corebackend.dto.user.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.personnelmanagement.PersonalBasicInfo;
import com.aes.corebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/")
    public String hello() {

        return "Hello AES";
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        //TODO convert dto to entity
        User user = userDTO.getUserEntity(userDTO);
        boolean success = userService.createUser(user);
        return ResponseEntity.ok(new PersonnelManagementResponseDTO("User creation successful!", success, null));
    }


}
