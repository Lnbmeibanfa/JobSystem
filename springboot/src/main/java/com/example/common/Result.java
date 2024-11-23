package com.example.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    private String code;
    private String msg;
    private Object data;


    public static Result success () {
        Result result = new Result();
        result.code = "200";
        result.msg = "请求成功";
        return result;
    }

    public static Result success (Object data) {
        Result result = success();
        result.data = data;
        return result;
    }

    public static Result error () {
        Result result = new Result();
        result.code = "500";
        result.msg = "系统异常";
        return result;
    }

    public static Result error (String msg) {
        Result result = new Result();
        result.code = "500";
        result.msg = msg;
        return result;
    }

    public static Result error (String code, String msg) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        return result;
    }

}
