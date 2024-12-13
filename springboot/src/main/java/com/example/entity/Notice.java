package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2024-12-8
 */
@Getter
@Setter
public class Notice {
    private Integer id;
    private String content;
    private String time;
    private String title;
}
