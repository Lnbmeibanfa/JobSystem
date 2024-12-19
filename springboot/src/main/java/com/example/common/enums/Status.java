package com.example.common.enums;

/**
 * @author 林万奇
 * @since 2024-12-8
 */
public enum Status {
    PASS("审核通过"),
    WAIT("待审核"),
    FAIL("审核不通过")
    ;
    private String status;

    Status(String status) {}
}
