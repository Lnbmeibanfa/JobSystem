package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Industry;
import com.example.mapper.IndustryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {
    @Resource
    private IndustryMapper industryMapper;

    public void add (Industry industry) {
        industryMapper.insert(industry);
    }

    public PageInfo<Industry> selectByPage(Industry industry, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Industry> list = industryMapper.selectByPage(industry);
        return PageInfo.of(list);
    }

    public void update(Industry industry) {
        industryMapper.update(industry);
    }

    public void deleteById(Integer id) {
        industryMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            industryMapper.deleteById(id);
        }
    }


    public List<Industry> selectAll() {
        return industryMapper.selectAll();
    }
}
