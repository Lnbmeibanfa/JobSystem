package com.example.entity;

import com.example.entity.experience.EduExp;
import com.example.entity.experience.ProExp;
import com.example.entity.experience.WorkExp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-04
 */
@Getter
@Setter
public class Resume {
    private Integer id;
    private String name;
    private String username;
    private String sex;
    private String salary;
    private String education;
    private String experience;
    private String phone;
    private String email;
    private String eduExp;
    private List<EduExp> eduExpList;
    private String workExp;
    private List<WorkExp> workExpList;
    private String proExp;
    private List<ProExp> proExpList;
    private Integer userId;
}
