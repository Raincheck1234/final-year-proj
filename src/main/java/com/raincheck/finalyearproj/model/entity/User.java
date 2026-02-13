package com.raincheck.finalyearproj.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String role; // STUDENT 或 STAFF
    private String name;
    private String email;
    private String passwordHash;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}