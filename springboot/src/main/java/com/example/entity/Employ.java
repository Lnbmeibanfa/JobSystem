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
public class Employ extends Account {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String city;
    private String address;
    private Integer industryId;
    private String scale;
    private String stage;
    private String status;
    private String industryName;
}
