package com.aes.corebackend.integrationtest.usermanagement;

import com.aes.corebackend.service.usermanagement.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserControllerTest {

    public MockMvc mockMvc;

    @Autowired
    private UserService userService;
}
