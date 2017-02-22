package com.zhangry.ssh.service.impl;

import com.zhangry.ssh.entity.User;
import com.zhangry.ssh.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangry on 2017/2/22.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    public List<User> getUserList() {
        return null;
    }
}
