package com.example.springboot.entity;


/**
 * Result 类
 * 属性
 *  - code 状态码
 *  - msg 提示信息
 *  - data 数据
 *  方法
 *  - success() 成功调用
 *  - error() 失败调用
 *  -
 */
@lombok.Data
public class Result {
    private String  code;
    private String msg;
    private Object data;
    public Result () {

    }
    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("请求成功");
        return result;
    }
    public static Result success(Object data) {
        Result result = success();
        result.setData(data);
        return result;
    }
    public static Result error() {
        Result result = new Result();
        result.setCode("500");
        result.setMsg("请求失败");
        return result;
    }
    public static Result error(String msg) {
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
