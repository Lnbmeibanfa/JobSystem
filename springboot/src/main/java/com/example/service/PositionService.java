package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Employ;
import com.example.entity.Position;
import com.example.exception.CustomException;
import com.example.mapper.EmployMapper;
import com.example.mapper.PositionMapper;
import com.example.util.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    @Resource
    private PositionMapper positionMapper;
    @Autowired
    private EmployMapper employMapper;

    public void add (Position position) {
        Account currentUser = JWTUtil.getCurAccount();
        if (currentUser == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Employ dbemploy = employMapper.selectById(currentUser.getId());
        position.setEmployId(dbemploy.getId());
        position.setIndustryId(dbemploy.getIndustryId());
        position.setStatus("待审核");
        positionMapper.insert(position);
    }

    public PageInfo<Position> selectByPage(Position position, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = JWTUtil.getCurAccount();
        if (currentUser == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (currentUser.getRole().equals(RoleEnum.EMPLOY.name())) {
            position.setEmployId(currentUser.getId());
        }
        List<Position> list = positionMapper.selectByPage(position);
        return PageInfo.of(list);
    }

    public void update(Position position) {
        positionMapper.update(position);
    }

    public void deleteById(Integer id) {
        positionMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            positionMapper.deleteById(id);
        }
    }


    public List<Position> selectAll(Position position) {
        return positionMapper.selectAll(position);
    }
}
