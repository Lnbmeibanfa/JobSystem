package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWT;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.admin.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.util.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Resource
    AdminMapper adminMapper;

    public void add (Admin admin) {
        Admin dbAdmin = adminMapper.selectById(admin.getId());
        // 校验用户是否已存在
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        initAdmin(admin);
        admin.setRole(RoleEnum.ADMIN.name());
        adminMapper.insert(admin);
    }

    public PageInfo<Admin> selectByPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectByPage(admin);
        return PageInfo.of(list);
    }

    public Admin selectById(Integer id) {
        Admin dbAdmin = adminMapper.selectById(id);
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        return dbAdmin;
    }

    public void update(Admin admin) {
        // 校验是否有用户存在
        Admin dbAdmin = adminMapper.selectById(admin.getId());
        if (!ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        initAdmin(admin);
        adminMapper.update(admin);
    }

    private void initAdmin (Admin admin) {
        if (ObjectUtil.isEmpty(admin.getUsername())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword("123456");
        }
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
    }

    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
    }

    public Account login(Account account) {
        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        } else if (!dbAdmin.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        // token
        String token = JWTUtil.createJWT(dbAdmin.getId() + "-" + dbAdmin.getRole(), dbAdmin.getPassword());
        dbAdmin.setToken(token);
        return dbAdmin;
    }

    public List<Admin> selectAll() {
        return adminMapper.selectAll();
    }
}
