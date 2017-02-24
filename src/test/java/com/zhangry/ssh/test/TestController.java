package com.zhangry.ssh.test;

import com.zhangry.ssh.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhangry on 2017/2/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestController {

    // 测试业务层的类
    // @Resource注入业务层的类
    @Autowired
    private UserService userService;

    @Test
    public void demo() {
        userService.getUserByName("ZHANGSAN");
    }
}
