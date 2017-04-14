package com.zhangry.ssh.dao;

import com.zhangry.ssh.entity.User;

import java.util.List;

/**
 * Created by zhangry on 2017/2/22.
 */
public interface UserDao extends BaseDao<User, String> {

    void getUserByName(String name);

    List<User> getUserList();

}
