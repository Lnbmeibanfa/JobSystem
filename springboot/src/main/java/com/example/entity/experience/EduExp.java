package com.example.entity.experience;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 林万奇
 * @since 2025-01-04
 */
@Getter
@Setter
public class EduExp {
    private String schoolName;
    private String majorName;
    private String educationDegree;
    private LocalDate beginTime;
    private LocalDate endTime;
    private String majorCourse;
}
