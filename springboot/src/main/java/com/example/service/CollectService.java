package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Collect;
import com.example.entity.Position;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.CollectMapper;
import com.example.mapper.PositionMapper;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectService {
    @Resource
    private CollectMapper collectMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PositionService positionService;

    public void add (Collect collect) {
        List<Collect> dbCollect = collectMapper.selectByPage(collect);
        if (!dbCollect.isEmpty()) {
            throw new CustomException("-1", "已经收藏该职位,请勿重复收藏");
        }
        collectMapper.insert(collect);
    }

    public PageInfo<Collect> selectByPage(Collect collect, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Collect> list = collectMapper.selectByPage(collect);
        return PageInfo.of(list);
    }

    public void update(Collect collect) {
        collectMapper.update(collect);
    }

    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            collectMapper.deleteById(id);
        }
    }


    public List<Collect> selectAll() {
        List<Collect> collects = collectMapper.selectAll();
        if (!collects.isEmpty()) {
            for (Collect collect : collects) {
                try {
                    Integer positionId = collect.getPositionId();
                    Position dbPosition = positionService.selectById(positionId);
                    collect.setEmployAvatar(dbPosition.getEmployAvatar());
                    collect.setEmployName(dbPosition.getEmployName());
                    collect.setTagList(dbPosition.getTagList());
                    collect.setSalary(dbPosition.getSalary());
                    collect.setIndustryName(dbPosition.getIndustryName());
                    collect.setPositionName(dbPosition.getName());
                } catch (CustomException e) {
                    throw new CustomException("-1", "数据异常");
                }
            }
        }
        return collects;
    }
}
