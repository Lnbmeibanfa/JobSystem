package com.example.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 林万奇
 * @since 2025-01-21
 */
@Getter
@Setter
public class RelateDTO {
    private Integer positionId;
    private Integer userId;
    private Integer index;
    public RelateDTO(Integer positionId, Integer userId, Integer index) {
        this.positionId = positionId;
        this.userId = userId;
        this.index = index;
    }
}
