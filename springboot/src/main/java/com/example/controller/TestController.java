package com.example.controller;

import com.example.common.Result;
import com.example.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result hello() {
        throw new CustomException("-1", "后台出现错误");
    }
}
