package com.example.controller;

import com.example.common.Result;
import com.example.entity.Resume;
import com.example.service.ResumeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Resource
    private ResumeService resumeService;

    @PostMapping("/add")
    public Result add (@RequestBody Resume resume) {
        resumeService.add(resume);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public Result selectPage(Resume resume,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Resume> pageinfo = resumeService.selectByPage(resume, pageNum, pageSize);
        return Result.success(pageinfo);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Resume> resumes = resumeService.selectAll();
        return Result.success(resumes);
    }

    @PutMapping("/update")
    public Result update (@RequestBody Resume resume) {
        resumeService.update(resume);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteById (@PathVariable Integer id) {
        resumeService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteByIds (@RequestBody List<Integer> ids) {
        resumeService.deleteBatch(ids);
        return Result.success();
    }

}
