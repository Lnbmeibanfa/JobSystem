package com.example.exception;

import com.example.common.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String code;
    private String message;

    public CustomException(String code, String msg) {
        this.code = code;
        this.message = msg;
    }
    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.message = resultCodeEnum.message;
    }
}
