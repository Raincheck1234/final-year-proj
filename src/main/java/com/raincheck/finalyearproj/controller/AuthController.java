package com.raincheck.finalyearproj.controller;

import com.raincheck.finalyearproj.common.Result;
import com.raincheck.finalyearproj.common.UserContext;
import com.raincheck.finalyearproj.model.dto.PasswordUpdateDTO;
import com.raincheck.finalyearproj.model.entity.User;
import com.raincheck.finalyearproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        return Result.success(userService.register(user));
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> loginData) {
        String token = userService.login(loginData.get("email"), loginData.get("password"));
        return Result.success(token);
    }

    @PostMapping("/update-password")
    public Result<String> updatePassword(@RequestBody PasswordUpdateDTO dto){
        String email = UserContext.getUser();
        userService.updatePassword(email, dto.getOldPassword(), dto.getNewPassword());
        return Result.success("Update Success");
    }
}