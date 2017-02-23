package com.zhangry.ssh.service.impl;

import com.zhangry.common.exception.ServiceException;
import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.common.util.MapperUtil;
import com.zhangry.ssh.dao.UserDao;
import com.zhangry.ssh.entity.User;
import com.zhangry.ssh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 分页获取事件列表
     *
     * @author zhaohuan
     * @date 2017/2/22
     * @param queryParameter 分页对象
     * @param params 查询条件  {EeventType:事件类型; eventStatus:实际状态; reportTime: 上报时间 }
     * @return
     */
    @Override
    public String getUserList(QueryParameter queryParameter, Map<String, Object> params, String... columns) {
        Page<User> page = null;
        try {
            page = userDao.getUserList(queryParameter, params);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            throw new ServiceException("查询事件列表失败！", e);
        }
        //将结果集转换为带分页的JSON
        return MapperUtil.convertToJson(page, columns);

    }

}
