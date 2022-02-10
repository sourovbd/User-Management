package com.aes.corebackend.repository;

import com.aes.corebackend.dto.UserDTO;
import com.aes.corebackend.entity.User;
import com.aes.corebackend.entity.UserCredential;
import com.aes.corebackend.util.response.APIResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 @DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    UserCredential userCredential_1 = new UserCredential(1,"101","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_2 = new UserCredential(2,"102","a1wq",true,"EMPLOYEE");
    UserCredential userCredential_3 = new UserCredential(3,"103","a1wq",true,"EMPLOYEE");
    User user_1 = new User(1L,"abc@gmail.com","agm","101","a1polymar","accounts","EMPLOYEE",userCredential_1, null, null);
    User user_2 = new User(2L,"abd@gmail.com","agm","102","a1polymar","accounts","EMPLOYEE",userCredential_2, null, null);
    User user_3 = new User(3L,"abe@gmail.com","agm","103","a1polymar","accounts","EMPLOYEE",userCredential_3, null, null);

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

        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user created successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(user);
        userRepository.save(user);
    }

    @Test
    public void getAllUsers_success() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user_1,user_2,user_3));
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user fetch ok");
        responseDTO.setSuccess(true);
        responseDTO.setData(users);
        userRepository.findAll();
    }
    @Test
    public void getUserDetailsTest() throws Exception {
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user found");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1);
        userRepository.findById(1L);
    }

    @Test
    public void updateUserById() throws Exception {
        User user_1_temp = new User(1L,"abc@gmail.com","dgm","0101","a1polymar","accounts","EMPLOYEE",userCredential_1, null, null);
        APIResponse responseDTO = new APIResponse();
        responseDTO.setMessage("user updated successfully");
        responseDTO.setSuccess(true);
        responseDTO.setData(user_1_temp);
        userRepository.save(user_1_temp);
    }
}
