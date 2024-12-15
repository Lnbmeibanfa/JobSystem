package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.util.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AccountServiceImpl {
    @Resource
    UserMapper userMapper;

    public void add (User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        // 校验用户是否已存在
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        initAccount(user);
        user.setRole(RoleEnum.ADMIN.name());
        userMapper.insert(user);
    }

    public PageInfo<User> selectByPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectByPage(user);
        return PageInfo.of(list);
    }

    public User selectById(Integer id) {
        User dbUser = userMapper.selectById(id);
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        return dbUser;
    }

    public void update(User user) {
        // 校验是否有用户存在
        User dbUser = userMapper.selectById(user.getId());
        if (!ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        initAccount(user);
        userMapper.update(user);
    }

    @Override
    protected void initSpecific(Account account) {
        if (account instanceof User user && ObjectUtil.isNull(user.getName())) {
            user.setName(user.getUsername());
        }
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.deleteById(id);
        }
    }

    public Account login(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        } else if (!dbUser.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        // token
        String token = JWTUtil.createJWT(dbUser.getId() + "-" + dbUser.getRole(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!dbUser.getPassword().equals(account.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        if (dbUser.getPassword().equals(account.getNewPassword())) {
            return ;
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.update(dbUser);
    }
}
