package com.aes.corebackend.service;

import com.aes.corebackend.entity.User;
import com.aes.corebackend.repository.UserRepository;
import org.apache.el.stream.Stream;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
class UserServiceTest {
    @InjectMocks
    private UserService service ;
    @Mock
    private UserRepository repository;

    @Test
    public void saveUserTest() {
        User user = new User(1, "ahad.alam@anwargroup.net", "agm", "0101", "a1polymar", "accounts");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user,service.save(user));
    }

    @Test
    void update() {
    }

    @Test
    public void findByIdTest() {
        long id = 1;
        User userMock = new User(1, "ahad.alam@anwargroup.net", "agm", "0101", "a1polymar", "accounts");
        when(repository.findById(id)).thenReturn(userMock);
        //User userService = service.findById(id);

    }
}