package com.example.controller;

import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Position;
import com.example.exception.CustomException;
import com.example.mapper.PositionMapper;
import com.example.service.PositionService;
import com.example.util.JWTUtil;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/position")
public class PositionController {

    @Resource
    private PositionService positionService;

    @PostMapping("/add")
    public Result add (@RequestBody Position position) {
        positionService.add(position);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Position position,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Position> pageinfo = positionService.selectByPage(position, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll(Position position) {
        List<Position> positions = positionService.selectAll(position);
        return Result.success(positions);
    }

    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        Position position = positionService.selectById(id);
        return Result.success(position);
    }

    @GetMapping("/selectRecommend")
    public Result selectRecommend() {
        List<Position> positions = positionService.selectRecommend();
        return Result.success(positions);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Position position) {
        positionService.update(position);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        positionService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        positionService.deleteBatch(ids);
        return Result.success();
    }

}
