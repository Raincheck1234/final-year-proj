package com.raincheck.finalyearproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.raincheck.finalyearproj.model.entity.User;

public interface UserService extends IService<User> {
    String register(User user);
    String login(String email, String password);
    boolean updatePassword(String email, String oldPassword, String newPassword);
}