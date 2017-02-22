package com.zhangry.ssh.dao.impl;

import com.zhangry.ssh.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangry on 2017/2/22.
 */
@Repository
public class UserDaoImpl extends HibernateDAO<User, String> implements UserDAO {
}
