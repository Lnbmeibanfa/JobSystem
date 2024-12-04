package com.example.entity;

import com.example.common.enums.admin.RoleEnum;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Admin {

    private int id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private String role;

}
