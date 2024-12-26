package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2024-12-23
 */
@Getter
@Setter
public class Advertise {
    private Integer id;
    private String img;
    private int positionId;
    private String location;
    private String positionName;
    private String employName;
    private String positionSalary;
}
