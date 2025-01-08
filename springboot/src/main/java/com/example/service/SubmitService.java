package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Submit;
import com.example.exception.CustomException;
import com.example.mapper.SubmitMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubmitService {
    @Resource
    private SubmitMapper submitMapper;

    public void add (Submit submit) {
        Integer userId = submit.getUserId();
        Integer positionId = submit.getPositionId();
        if (userId == null || positionId == null) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        List<Submit> dbSubmitList = submitMapper.selectByUserIdAndPositionId(userId, positionId);
        if (dbSubmitList.size() > 1) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        if (dbSubmitList.size() == 1) {
            if (dbSubmitList.getFirst().getResumeId().equals(submit.getResumeId()) && dbSubmitList.getFirst().getPositionId().equals(positionId)) {
                throw new CustomException("-1", "请勿重复投递,详情请在\"我的投递\"界面查看");
            }
        }
        submit.setTime(LocalDate.now());
        submit.setStatus("已投递");
        submitMapper.insert(submit);
    }

    public PageInfo<Submit> selectByPage(Submit submit, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Submit> list = submitMapper.selectByPage(submit);
        return PageInfo.of(list);
    }

    public void update(Submit submit) {
        submitMapper.update(submit);
    }

    public void deleteById(Integer id) {
        submitMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            submitMapper.deleteById(id);
        }
    }


    public List<Submit> selectAll(Integer userId) {
        return submitMapper.selectAll(userId);
    }
}
