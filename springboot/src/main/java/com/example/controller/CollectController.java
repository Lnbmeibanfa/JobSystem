package com.example.controller;

import com.example.common.Result;
import com.example.entity.Collect;
import com.example.service.CollectService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    @PostMapping("/add")
    public Result add (@RequestBody Collect collect) {
        collectService.add(collect);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Collect collect,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Collect> pageinfo = collectService.selectByPage(collect, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Collect> collects = collectService.selectAll();
        return Result.success(collects);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Collect collect) {
        collectService.update(collect);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        collectService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        collectService.deleteBatch(ids);
        return Result.success();
    }

}
