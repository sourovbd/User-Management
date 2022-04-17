package com.sv.corebackend.unittest.service;
import com.sv.corebackend.dto.usermanagement.UserDTO;
import com.sv.corebackend.entity.User;
import com.sv.corebackend.entity.UserCredential;
import com.sv.corebackend.repository.UserRepository;
import com.sv.corebackend.service.usermanagement.EmailSender;
import com.sv.corebackend.service.usermanagement.UserService;
import com.sv.corebackend.util.response.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.corebackend.util.response.UMAPIResponseMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailSender emailSender;

    ObjectMapper om = new ObjectMapper();
    UserCredential userCredential_1 = createUserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = createUserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = createUserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = createUser(1L,"abc@gmail.com","agm","101","a1polymar","accounts","EMPLOYEE",userCredential_1);
    User user_2 = createUser(2L,"abd@gmail.com","agm","102","a1polymar","accounts","EMPLOYEE",userCredential_2);
    User user_3 = createUser(3L,"abe@gmail.com","agm","103","a1polymar","accounts","EMPLOYEE",userCredential_3);

    @Test
    public void createUserTest() throws Exception {
        UserCredential userCredential = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
        User user = new User();
        user.setId(1L);
        user.setDesignation("agm");
        user.setDepartment("accounts");
        user.setEmailAddress("mdahad118@gmail.com");
        user.setBusinessUnit("a1polymar");
        user.setEmployeeId("101");
        user.setRoles("EMPLOYEE");
        user.setUserCredential(userCredential);
        UserDTO userDto = new UserDTO();

        userDto.setDesignation("agm");
        userDto.setDepartment("accounts");
        userDto.setEmailAddress("mdahad118@gmail.com");
        userDto.setBusinessUnit("a1polymar");
        userDto.setEmployeeId("101");
        userDto.setRoles("EMPLOYEE");
        // UserCredential userCredential = new UserCredential(1L,"101","a1wq",true,"EMPLOYEE");

        Mockito.when(userRepository.save(user)).thenReturn(user);
        APIResponse returnedResponse = userService.create(user,userDto);
        assertEquals(returnedResponse.getData(),user);
    }

    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user fetch ok");
        responseDTO.setSuccess(true);
        responseDTO.setData(users);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        APIResponse returnedResponse = userService.read();
        assertEquals(returnedResponse.getData(),responseDTO.getData());
    }

    @Test
    @Disabled
    public void getUserDetailsTest() throws Exception {
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user found");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user_1));
        APIResponse returnedResponse = userService.read(1L);
        assertEquals(returnedResponse.getData(),responseDTO.getData());
    }

    @Test
    public void getUserDetailsFailedTest() throws Exception {
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user not found");
        responseDTO.setSuccess(false);
        responseDTO.setData(null);
        Mockito.when(userRepository.findById(4L)).thenReturn(Optional.ofNullable(null));
        APIResponse returnedResponse = userService.read(4L);
        assertEquals(returnedResponse.getData(),responseDTO.getData());
    }

    @Test
    public void updateUserById() throws Exception {
        User user_1_temp = new User(1L,"abc@gmail.com","dgm","0101","a1polymar","accounts","EMPLOYEE",userCredential_1);
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user updated successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1_temp);
        UserDTO userDto = new UserDTO();
        userDto.setDesignation("dgm");
        userDto.setDepartment("accounts");
        userDto.setEmailAddress("abc@gmail.com");
        userDto.setBusinessUnit("a1polymar");
        userDto.setEmployeeId("0101");
        userDto.setRoles("EMPLOYEE");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user_1));
        Mockito.when(userRepository.save(user_1_temp)).thenReturn(user_1_temp);
        APIResponse returnedResponse = userService.update(userDto ,1L);
        assertEquals(returnedResponse.getData(),responseDTO.getData());
    }

    @Test
    public void updateUserByIdFail() throws Exception {
        User user_1_temp = new User(1L,"abc@gmail.com","dgm","0101","a1polymar","accounts","EMPLOYEE",userCredential_1);
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage(UMAPIResponseMessage.USER_UPDATE_FAILED);
        responseDTO.setSuccess(false);
        responseDTO.setData(user_1_temp);
        UserDTO userDto = new UserDTO();
        userDto.setDesignation("dgm");
        userDto.setDepartment("accounts");
        userDto.setEmailAddress("abc@gmail.com");
        userDto.setBusinessUnit("a1polymar");
        userDto.setEmployeeId("0101");
        userDto.setRoles("EMPLOYEE");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user_1));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);
        APIResponse returnedResponse = userService.update(userDto ,1L);
        assertEquals(returnedResponse.getData(),responseDTO.getData());
    }

    public UserCredential createUserCredential(long id, String employeeId, String password, boolean flag, String roles) {

        return new UserCredential(id, employeeId, password, flag, roles);
    }

    public User createUser(long id, String emailAddress, String designation, String employeeId, String businessUnit, String department, String roles, UserCredential userCredential) {

        return new User(id, emailAddress, designation, employeeId, businessUnit, department, roles, userCredential);
    }
}