package com.aes.corebackend.repository;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    UserCredential userCredential_1 = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = new UserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = new UserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = new User(1L,"abc@gmail.com","agm","101","a1polymar","accounts","EMPLOYEE",userCredential_1, null, null);
    User user_2 = new User(2L,"abd@gmail.com","agm","102","a1polymar","accounts","EMPLOYEE",userCredential_2, null, null);
    User user_3 = new User(3L,"abe@gmail.com","agm","103","a1polymar","accounts","EMPLOYEE",userCredential_3, null, null);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserTest() {
        UserCredential userCredential = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
        User user = new User();
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("0101");
        user.setRoles("EMPLOYEE");
        user.setUserCredential(userCredential);

        UserDTO userDto = new UserDTO();
        userDto.setDesignation("agm");
        userDto.setDepartment("accounts");
        userDto.setEmailAddress("mdahad118@gmail.com");
        userDto.setBusinessUnit("a1polymar");
        userDto.setEmployeeId("0101");
        userDto.setRoles("EMPLOYEE");

        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user created successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user fetch ok");
        responseDTO.setSuccess(true);
        responseDTO.setData(users);
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }
    @Test
    public void getUserDetailsTest() throws Exception {
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user found");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user_1));
    }

    @Test
    public void updateUserById() throws Exception {
        User user_1_temp = new User(1L,"abc@gmail.com","dgm","0101","a1polymar","accounts","EMPLOYEE",userCredential_1, null, null);
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user updated successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1_temp);
        Mockito.when(userRepository.save(user_1_temp)).thenReturn(user_1_temp);
    }
}
