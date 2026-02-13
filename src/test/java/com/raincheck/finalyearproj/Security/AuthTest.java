package com.raincheck.finalyearproj.Security;

import com.raincheck.finalyearproj.model.entity.User;
import com.raincheck.finalyearproj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class AuthTest {

    @Autowired
    private UserService userService;

    /**
     * Register and login
     */
    @Test
    void testAuthFlow() {
        // 1. 模拟注册
        User user = new User();
        user.setEmail("test_student@bupt.edu.cn");
        user.setPasswordHash("123456");
        user.setName("Test Student");
        user.setRole("STUDENT");

        try {
            String regMsg = userService.register(user);
            log.info("Register Result: {}", regMsg);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

        // 2. 模拟登录
        String token = userService.login("test_student@bupt.edu.cn", "123456");
        log.info("Generated JWT: {}", token);

        assertNotNull(token);
    }

    /**
     * Password update
     */
    @Test
    void testPasswordUpdate() {
        try{
            userService.updatePassword("test_student@bupt.edu.cn", "123456", "123456");
        } catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("Password changed.");
    }

    /**
     * Non-existent login and duplicated registry
     */
    @Test
    void testAuthEdgeCases() {
        try {
            String token = userService.login("test_student@bupt.edu.cn", "12345");
        } catch (Exception e){
            log.error(e.getMessage());
        }

        try {
            userService.login("non_existent@bupt.edu.cn", "123456");
        } catch (Exception e){
            log.error(e.getMessage());
        }

        User duplicateUser = new User();
        duplicateUser.setEmail("test_student@bupt.edu.cn"); // 使用已注册的邮箱
        duplicateUser.setPasswordHash("any_pass");

        try {
            userService.register(duplicateUser);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
