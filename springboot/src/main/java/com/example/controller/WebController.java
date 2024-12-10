package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.admin.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理登录注册请求
 * @author 林万奇
 * @since 2024-12-8
 */
@Slf4j
@RestController
public class WebController {
    @Resource
    AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        log.info(account.toString());
        Account loginAccount = null;
        if (account.getRole().equals(RoleEnum.ADMIN.name())) {
            loginAccount = adminService.login(account);
        }
        return Result.success(loginAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        log.info(account.toString());
        return Result.success();
    }
}
