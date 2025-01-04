package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-03
 */
@Getter
@Setter
public class Collect {
    private Integer id;
    private Integer positionId;
    private Integer StudentId;
    private String positionName;
    private String tags;
    private List<String> tagList;
    private String salary;
    private String employName;
    private String employAvatar;
    private String industryName;
    private String employStage;
}
