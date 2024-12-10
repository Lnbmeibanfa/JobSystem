package com.example.entity;

import com.example.common.enums.admin.RoleEnum;
import lombok.Getter;
import lombok.Setter;
/**
 * Admin
 * @author 林万奇
 * @since 2024-12-8
 */
@Setter
@Getter
public class Admin extends Account {
    private int id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private String role;

}
