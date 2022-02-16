package com.aes.corebackend.unittest.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Manages Spring Application Context
 * */
@ExtendWith(SpringExtension.class)
/**
 * Populates our application context with all of our spring managed beans.
 * Manages false Spring Application Context
 * */
@SpringBootTest
/**
 * This creates mock mvc instance and wires into out test class.
 * */
@AutoConfigureMockMvc

public class UserCredentialControllerTest {

   /* @MockBean
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
    void testSaveCredential() throws Exception {
        //Setup mocked service
        User user = new User(1L,"abc@gmail.com","agm","101","a1polymar","accounts","EMPLOYEE", null, null, null);
        UserCredential userCredential = new UserCredential(27, "12471", "123456", true, "EMPLOYEE");
        user.setUserCredential(userCredential);
        System.out.println("user: "+user);

    }*/
}
