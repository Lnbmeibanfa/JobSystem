package com.example.entity.experience;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 林万奇
 * @since 2025-01-04
 */
@Getter
@Setter
public class WorkExp {
    private String companyName;
    private String projectName;
    private String positionName;
    private String positionType;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private String projectDesc;
}
