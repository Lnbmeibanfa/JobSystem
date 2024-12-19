package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 所有用户的父类
 * @author 林万奇
 * @since 2024-12-8
 */
@Getter
@Setter
public class Account {
    private String username;
    private String password;
    private String role;
    private String newPassword;
    private String token;
    private String name;
}
