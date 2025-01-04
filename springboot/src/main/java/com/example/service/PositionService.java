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
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Position selectById (Integer id) {
        Position position = positionMapper.selectById(id);
        if (position == null) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        position.setTagList(tagsToList(position.getTag()));
        return position;
    }

    public PageInfo<Position> selectByPage(Position position, Integer pageNum, Integer pageSize) {
        Account currentUser = JWTUtil.getCurAccount();
        if (currentUser == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (currentUser.getRole().equals(RoleEnum.EMPLOY.name())) {
            position.setEmployId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
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

    /**
     * 将tags转为tagList
     */
    private List<String> tagsToList (String tags) {
        if (tags == null) {
            return new ArrayList<>();
        }
        String[] split = tags.split(",");
        List<String> list = Arrays.asList(split);
        if (list.size() > 3) {
            return list.subList(0,3);
        } else {
            return list;
        }
    }

    public List<Position> selectAll(Position position) {
        List<Position> positions = positionMapper.selectAll(position);
        for (Position dbposition : positions) {
            String tags = dbposition.getTag();
            dbposition.setTagList(tagsToList(tags));
        }
        return positions;
    }

    public List<Position> selectRecommend() {
        Position position = new Position();
        List<Position> positions = positionMapper.selectAll(position).subList(0,3);
        for (Position dbposition : positions) {
            String tags = dbposition.getTag();
            dbposition.setTagList(tagsToList(tags));
        }
        return positions;
    }
}
