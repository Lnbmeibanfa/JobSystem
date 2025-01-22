package com.example.controller;

import com.example.common.Result;
import com.example.entity.Submit;
import com.example.mapper.SubmitMapper;
import com.example.service.SubmitService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Resource
    private SubmitService submitService;
    @Autowired
    private SubmitMapper submitMapper;

    @PostMapping("/add")
    public Result add (@RequestBody Submit submit) {
        submitService.add(submit);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Submit submit,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Submit> pageinfo = submitService.selectByPage(submit, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Submit submit) {
        List<Submit> submits = submitService.selectAll(submit);
        return Result.success(submits);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Submit submit) {
        submitService.update(submit);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        submitService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        submitService.deleteBatch(ids);
        return Result.success();
    }

}
