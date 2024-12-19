package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Employ;
import com.example.service.EmployService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employ")
public class EmployController {

    @Resource
    private EmployService employService;

    @PostMapping("/add")
    public Result add (@RequestBody Employ employ) {
        employService.add(employ);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Employ employ,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize
                             ) {
        PageInfo<Employ> pageinfo = employService.selectByPage(employ, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Employ> employs = employService.selectAll();
        return Result.success(employs);
    }

    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        Employ employ = employService.selectById(id);
        return Result.success(employ);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Employ employ) {
        employService.update(employ);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        employService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        employService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update/password")
    public Result updatePassword (@RequestBody Account account) {
        if (account.getRole().equals(RoleEnum.ADMIN.name())) {
            employService.updatePassword(account);

        }
        return Result.success();
    }

}
