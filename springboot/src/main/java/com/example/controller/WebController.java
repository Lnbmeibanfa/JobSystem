package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Employ;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.service.EmployService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;

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
    @Resource
    EmployService employService;
    @Resource
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account loginAccount = null;
        if (account.getRole().equals(RoleEnum.ADMIN.name())) {
            loginAccount = adminService.login(account);
        } else if(account.getRole().equals(RoleEnum.EMPLOY.name())) {
            loginAccount = employService.login(account);
        } else if (account.getRole().equals(RoleEnum.USER.name())) {
            loginAccount = userService.login(account);
        }
        return Result.success(loginAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (account.getRole().equals(RoleEnum.USER.name())) {
            userService.register(account);
        } else if (account.getRole().equals(RoleEnum.EMPLOY.name())) {
            employService.register(account);
        }
        return Result.success();
    }

    @PutMapping("/update/password")
    public Result updatePassword (@RequestBody Account account) {
        if (account.getRole().equals(RoleEnum.ADMIN.name())) {
            adminService.updatePassword(account);
        } else if (account.getRole().equals(RoleEnum.EMPLOY.name())) {
            employService.updatePassword(account);
        } else if (account.getRole().equals(RoleEnum.USER.name())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }
}
