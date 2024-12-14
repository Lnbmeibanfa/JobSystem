package com.example.controller;

import com.example.common.Result;
import com.example.entity.Industry;
import com.example.service.IndustryService;
import com.example.service.IndustryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/industry")
public class IndustryController {

    @Resource
    private IndustryService industryService;

    @PostMapping("/add")
    public Result add (@RequestBody Industry industry) {
        industryService.add(industry);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Industry industry,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Industry> pageinfo = industryService.selectByPage(industry, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Industry> industrys = industryService.selectAll();
        return Result.success(industrys);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Industry industry) {
        industryService.update(industry);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        industryService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        industryService.deleteBatch(ids);
        return Result.success();
    }

}
