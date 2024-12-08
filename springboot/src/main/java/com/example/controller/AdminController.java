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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Result add (@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Admin> pageinfo = adminService.selectAll(admin, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        adminService.deleteBatch(ids);
        return Result.success();
    }

}
