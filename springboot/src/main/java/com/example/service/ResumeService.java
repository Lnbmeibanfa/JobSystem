package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.entity.Resume;
import com.example.mapper.ResumeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {
    @Resource
    private ResumeMapper resumeMapper;

    public void add (Resume resume) {
        resume.setEduExp(JSONUtil.toJsonStr(resume.getEduExpList()));
        resume.setWorkExp(JSONUtil.toJsonStr(resume.getWorkExpList()));
        resume.setProExp(JSONUtil.toJsonStr(resume.getProExpList()));
        resumeMapper.insert(resume);
    }

    public PageInfo<Resume> selectByPage(Resume resume, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Resume> list = resumeMapper.selectByPage(resume);
        return PageInfo.of(list);
    }

    public void update(Resume resume) {
        resumeMapper.update(resume);
    }

    public void deleteById(Integer id) {
        resumeMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            resumeMapper.deleteById(id);
        }
    }


    public List<Resume> selectAll() {
        return resumeMapper.selectAll();
    }
}
