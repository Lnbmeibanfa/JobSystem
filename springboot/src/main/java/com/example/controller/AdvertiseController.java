package com.example.controller;

import com.example.common.Result;
import com.example.entity.Advertise;
import com.example.service.AdvertiseService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/advertise")
public class AdvertiseController {

    @Resource
    private AdvertiseService advertiseService;

    @PostMapping("/add")
    public Result add (@RequestBody Advertise advertise) {
        advertiseService.add(advertise);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Advertise advertise,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Advertise> pageinfo = advertiseService.selectByPage(advertise, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Advertise> advertises = advertiseService.selectAll();
        return Result.success(advertises);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Advertise advertise) {
        advertiseService.update(advertise);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        advertiseService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        advertiseService.deleteBatch(ids);
        return Result.success();
    }

}
