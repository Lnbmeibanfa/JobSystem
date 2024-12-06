package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Result add (@RequestBody Admin admin) {
        return Result.error(ResultCodeEnum.PARAM_ERROR.message);
//        adminService.add(admin);
//        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info(String.valueOf(admin));
        log.info("pageNUm:{}", pageNum);
        log.info("pageSize:{}", pageSize);
        PageInfo<Admin> pageinfo = adminService.selectAll(admin, pageNum, pageSize);
        return Result.success(pageinfo);
    }
}
