package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result hello() {
        return Result.success("hello");
    }
}
