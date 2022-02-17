package com.aes.corebackend.integrationtest.controller;

import com.aes.corebackend.dto.usermanagement.ForgotPasswordDTO;
import com.aes.corebackend.dto.usermanagement.UserCredentialDTO;
import com.aes.corebackend.entity.usermanagement.User;
import com.aes.corebackend.entity.usermanagement.UserCredential;
import com.aes.corebackend.repository.usermanagement.UserCredentialRepository;
import com.aes.corebackend.repository.usermanagement.UserRepository;
import com.aes.corebackend.service.springsecurity.CustomUserDetailsService;
import com.aes.corebackend.service.usermanagement.UserCredentialService;
import com.aes.corebackend.util.Constants;
import com.aes.corebackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.Objects;

import static com.aes.corebackend.util.response.APIResponseStatus.ERROR;
import static com.aes.corebackend.util.response.APIResponseStatus.SUCCESS;
import static com.aes.corebackend.util.response.UMAPIResponseMessage.NEW_PASSWORD_SENT;
import static com.aes.corebackend.util.response.UMAPIResponseMessage.TRUE;
import static javax.persistence.criteria.JoinType.INNER;
import static org.hibernate.hql.internal.antlr.SqlTokenTypes.ON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserCredentialControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    private static String USERNAME = "012518";
    private static String TOKEN = "";
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        userDetails = userDetailsService.loadUserByUsername(USERNAME);
        System.out.println("username:  "+userDetails.getUsername());
        TOKEN = jwtTokenUtil.generateToken(userDetails);
    }

    @Test
    //@DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/users.xml")
    public void saveCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setId(1L);
        userCredentialDTO.setEmployeeId("012518");
        userCredentialDTO.setPassword("1234@Aa8");
        userCredentialDTO.setActive(true);
        userCredentialDTO.setRoles("EMPLOYEE");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users-credential")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCredentialDTO)))
                .andExpect(status().isOk());
    }

    @Test
    //@DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/users.xml")
    public void verifyCredentialTest() throws Exception {
        UserCredentialDTO userCredentialDTO = new UserCredentialDTO();
        userCredentialDTO.setId(2L);
        userCredentialDTO.setEmployeeId("012518");
        userCredentialDTO.setPassword("1234");
        userCredentialDTO.setActive(true);
        userCredentialDTO.setRoles("EMPLOYEE");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/verify-credential")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCredentialDTO)))
                        .andExpect(status().isOk());
    }
    @Test
    @Disabled
    //@DatabaseSetup("/dataset/user_credentials.xml")
    @DatabaseSetup("/dataset/users.xml")
    public void forgotPasswordTest() throws Exception {
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmailAddress("test2@gmail.com");
        userCredentialService.generateAndSendTempPass(forgotPasswordDTO.getEmailAddress());
    }

}
