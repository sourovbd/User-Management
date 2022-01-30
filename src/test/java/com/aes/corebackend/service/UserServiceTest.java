package com.aes.corebackend.service;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.apache.el.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    UserRepository repository;

    @Test
    public void save() {
    }

    @Test
    void update() {
    }

    @Test
    public void findByIdTest() {
        long id = 1;
        User userMock = new User(1, "ahad.alam@anwargroup.net", "agm", "0101", "a1polymar", "accounts");
        doReturn(userMock).when(service.findById(id));
        //User userService = service.findById(id);

    }
}