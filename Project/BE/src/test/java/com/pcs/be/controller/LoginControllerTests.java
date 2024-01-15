package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginControllerTests {
    @Autowired
    private LoginController loginController;

    @Test
    public void login() {
        try{
            Result<Object> result = loginController.login("Rosy");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
