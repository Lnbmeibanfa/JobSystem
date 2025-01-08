package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Resume;
import com.example.entity.experience.EduExp;
import com.example.entity.experience.ProExp;
import com.example.entity.experience.WorkExp;
import com.example.exception.CustomException;
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
        resumeMapper.insert(ListToJson(resume));
    }

    public PageInfo<Resume> selectByPage(Resume resume, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Resume> list = resumeMapper.selectByPage(resume);
        for (Resume r : list) {
            r.setEduExpList(JSONUtil.toList(resume.getEduExp(), EduExp.class));
            r.setWorkExpList(JSONUtil.toList(resume.getWorkExp(), WorkExp.class));
            r.setProExpList(JSONUtil.toList(resume.getProExp(), ProExp.class));
        }
        return PageInfo.of(list);
    }

    public void update(Resume resume) {
        resumeMapper.update(ListToJson(resume));
    }

    public void deleteById(Integer id) {
        resumeMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            resumeMapper.deleteById(id);
        }
    }


    public List<Resume> selectAll(Integer userId) {
        return resumeMapper.selectAll(userId);
    }

    public Resume selectById(Integer id) {
        List<Resume> list = resumeMapper.selectById(id);
        if (list.size() > 1){
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        Resume resume = list.get(0);
        return JsonToList(list.getFirst());
    }
    private Resume JsonToList (Resume resume) {
        resume.setEduExpList(JSONUtil.toList(resume.getEduExp(), EduExp.class));
        resume.setWorkExpList(JSONUtil.toList(resume.getWorkExp(), WorkExp.class));
        resume.setProExpList(JSONUtil.toList(resume.getProExp(), ProExp.class));
        return resume;
    }
    private  Resume ListToJson (Resume resume) {
        resume.setEduExp(JSONUtil.toJsonStr(resume.getEduExpList()));
        resume.setWorkExp(JSONUtil.toJsonStr(resume.getWorkExpList()));
        resume.setProExp(JSONUtil.toJsonStr(resume.getProExpList()));
        return resume;
    }

}
