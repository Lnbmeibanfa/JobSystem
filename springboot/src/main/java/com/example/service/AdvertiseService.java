package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Advertise;
import com.example.exception.CustomException;
import com.example.mapper.AdvertiseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiseService {
    @Resource
    private AdvertiseMapper advertiseMapper;

    public void add (Advertise advertise) {
        advertiseMapper.insert(advertise);
    }

    public PageInfo<Advertise> selectByPage(Advertise advertise, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Advertise> list = advertiseMapper.selectByPage(advertise);
        return PageInfo.of(list);
    }

    public void update(Advertise advertise) {
        List<Advertise> dbAdvertise = advertiseMapper.selectByLocation(advertise.getLocation());
        if (!dbAdvertise.isEmpty() && !dbAdvertise.get(0).getId().equals(advertise.getId())) {
            throw new CustomException("-1", "此位置已被占用");
        }
        advertiseMapper.update(advertise);
    }

    public void deleteById(Integer id) {
        advertiseMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            advertiseMapper.deleteById(id);
        }
    }


    public List<Advertise> selectAll() {
        return advertiseMapper.selectAll();
    }
}
