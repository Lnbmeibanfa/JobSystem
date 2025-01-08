package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author 林万奇
 * @since 2025-01-07
 */
@Getter
@Setter
public class Submit {
    private Integer id;
    private Integer employId;
    private String employName;
    private Integer positionId;
    private String positionName;
    private Integer userId;
    private String userName;
    private Integer resumeId;
    private String status;
    private LocalDate time;
}
