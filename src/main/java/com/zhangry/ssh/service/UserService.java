package com.zhangry.ssh.service;

import com.zhangry.ssh.entity.User;

import java.util.List;

/**
 * Created by zhangry on 2017/2/22.
 */
public interface UserService extends BaseService<User, String> {
    public List<User> getUserList();
}
