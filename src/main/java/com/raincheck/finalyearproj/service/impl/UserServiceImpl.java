package com.raincheck.finalyearproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.raincheck.finalyearproj.model.entity.User;
import com.raincheck.finalyearproj.mapper.UserMapper;
import com.raincheck.finalyearproj.service.UserService;
import com.raincheck.finalyearproj.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String register(User user) {
        // 1. 检查邮箱是否已被注册
        User existingUser = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, user.getEmail()));

        if (existingUser != null) {
            throw new RuntimeException("Email already registered!");
        }

        // 2. 加密密码
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());

        // 3. 保存到数据库
        boolean saved = this.save(user);
        return saved ? "Register success" : "Register failed";
    }

    @Override
    public String login(String email, String password) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));

        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            // 更新最后登录时间
            user.setLastLogin(LocalDateTime.now());
            this.updateById(user);

            return jwtUtils.createToken(user.getEmail(), user.getRole());
        }
        throw new RuntimeException("Login failed");
    }

    @Override
    public boolean updatePassword(String email, String oldPassword, String newPassword) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user == null) throw new RuntimeException("User not found");

        // 1. 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new RuntimeException("Old password incorrect");
        }

        // 2. 加密新密码并保存
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        return this.updateById(user);
    }
}