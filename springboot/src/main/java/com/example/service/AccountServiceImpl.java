package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 林万奇
 * @since 2024-12-8
 */
@Service
public abstract class AccountServiceImpl implements AccountSerive {
    @Override
    public void initAccount(Account account) {
        if (ObjectUtil.isEmpty(account.getUsername())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (ObjectUtil.isEmpty(account.getPassword())) {
            account.setPassword("123456");
        }
        initSpecific(account);
    }

    protected abstract void initSpecific(Account account);
}
