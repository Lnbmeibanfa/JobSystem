package com.example.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.example.controller")
public class GlobalExceptionHandler {
    private static final Log log = LogFactory.get();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        log.error("异常信息", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result exception(CustomException e) {
        log.error("custom异常信息", e);
        return Result.error(e.getCode(), e.getMessage());
    }
}
