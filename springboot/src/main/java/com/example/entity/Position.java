package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 林万奇
 * @since 2024-12-20
 */
@Getter
@Setter
public class Position {
    private Integer id;
    private String name;
    private Integer employId;
    private String employName;
    private String type;
    private String experience;
    private String salary;
    private String education;
    private String content;
    private String tag;
    private List<String> tagList;
    private String status;
    private Integer industryId;
    private String industryName;
    private String employAvatar;
    private String employStage;
    private String employCity;
    private String employScale;
    private String employAddress;
    private String positionName;
}
