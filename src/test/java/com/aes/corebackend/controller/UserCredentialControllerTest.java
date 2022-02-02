package com.aes.corebackend.controller;

import com.aes.corebackend.service.UserCredentialService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


@ExtendWith(SpringExtension.class) /** Manages Spring Application Context */
@SpringBootTest /** Populates our application context with all of our spring managed beans. Manages Spring Application Context */
@AutoConfigureMockMvc /** This creates mock mvc instance and wires into out test class. */
public class UserCredentialControllerTest {

    @MockBean
    private UserCredentialService userCredentialService;

    @Autowired
    private MockMvc mockMvc;

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");

    @BeforeAll
    static void beforeAll() {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Test
    @DisplayName("POST /users-credential")
    public void testSaveCredential() {

    }
}
