package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Admin
 * @author 林万奇
 * @since 2024-12-8
 */
@Setter
@Getter
public class User extends Account {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private String role;

}
