package com.aes.corebackend.service;

import com.aes.corebackend.dto.APIResponse;
import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    ObjectMapper om = new ObjectMapper();
    UserCredential userCredential_1 = createUserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = createUserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = createUserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = createUser(1L,"abc@gmail.com","agm","101","a1polymar","accounts","EMPLOYEE",userCredential_1);
    User user_2 = createUser(2L,"abd@gmail.com","agm","102","a1polymar","accounts","EMPLOYEE",userCredential_2);
    User user_3 = createUser(3L,"abe@gmail.com","agm","103","a1polymar","accounts","EMPLOYEE",userCredential_3);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = Mockito.mock(UserService.class);
    }

    @Test
    public void createUserTest() throws Exception {
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
        Mockito.when(userService.create(user,userDto)).thenReturn(responseDTO);
        assertEquals(userService.create(user,userDto).getMessage(),responseDTO.getMessage());
        assertEquals(userService.create(user,userDto).getData(),responseDTO.getData());
    }

    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user fetch ok");
        responseDTO.setSuccess(true);
        responseDTO.setData(users);
        Mockito.when(userService.read()).thenReturn(responseDTO);
    }

    @Test
    public void getUserDetailsTest() throws Exception {
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user found");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1);
        Mockito.when(userService.read(1L)).thenReturn(responseDTO);
    }

    @Test
    public void updateUserById() throws Exception {
        User user_1_temp = new User(1L,"abc@gmail.com","dgm","0101","a1polymar","accounts","EMPLOYEE",userCredential_1);
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user updated successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(null);
        Mockito.when(userService.update(user_1_temp,1L)).thenReturn(responseDTO);
        assertEquals(userService.update(user_1_temp,1L).getMessage(),responseDTO.getMessage());
        assertEquals(userService.update(user_1_temp,1L).getData(),responseDTO.getData());
    }

    public UserCredential createUserCredential(long id, String employeeId, String password, boolean flag, String roles) {

        return new UserCredential(id, employeeId, password, flag, roles);
    }

    public User createUser(long id, String emailAddress, String designation, String employeeId, String businessUnit, String department, String roles, UserCredential userCredential) {

        return new User(id, emailAddress, designation, employeeId, businessUnit, department, roles, userCredential);
    }
}