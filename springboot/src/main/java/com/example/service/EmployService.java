package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Employ;
import com.example.exception.CustomException;
import com.example.mapper.EmployMapper;
import com.example.util.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployService extends AccountServiceImpl {
    @Resource
    EmployMapper employMapper;

    public void add (Employ employ) {
        Employ dbEmploy = employMapper.selectByUsername(employ.getUsername());
        // 校验用户是否已存在
        if (ObjectUtil.isNotNull(dbEmploy)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        initAccount(employ);
        employ.setRole(RoleEnum.EMPLOYEE.name());
        employMapper.insert(employ);
    }

    public PageInfo<Employ> selectByPage(Employ employ, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employ> list = employMapper.selectByPage(employ);
        return PageInfo.of(list);
    }

    public Employ selectById(Integer id) {
        Employ dbEmploy = employMapper.selectById(id);
        if (ObjectUtil.isNull(dbEmploy)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        return dbEmploy;
    }

    public void update(Employ employ) {
        // 校验是否有用户存在
        Employ dbEmploy = employMapper.selectById(employ.getId());
        if (!ObjectUtil.isNotNull(dbEmploy)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        initAccount(employ);
        employMapper.update(employ);
    }

    @Override
    protected void initSpecific(Account account) {
        if (account instanceof Employ employ && ObjectUtil.isNull(employ.getName())) {
            employ.setName(employ.getUsername());
        }
    }

    public void deleteById(Integer id) {
        employMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            employMapper.deleteById(id);
        }
    }

    public Account login(Account account) {
        Employ dbEmploy = employMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbEmploy)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        } else if (!dbEmploy.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        // token
        String token = JWTUtil.createJWT(dbEmploy.getId() + "-" + dbEmploy.getRole(), dbEmploy.getPassword());
        dbEmploy.setToken(token);
        return dbEmploy;
    }

    public List<Employ> selectAll() {
        return employMapper.selectAll();
    }

    public void updatePassword(Account account) {
        Employ dbEmploy = employMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbEmploy)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!dbEmploy.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        if (dbEmploy.getPassword().equals(account.getNewPassword())) {
            return ;
        }
        dbEmploy.setPassword(account.getNewPassword());
        employMapper.update(dbEmploy);
    }
}
