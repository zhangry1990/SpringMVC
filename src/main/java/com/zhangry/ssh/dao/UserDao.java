package com.zhangry.ssh.dao;

import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.ssh.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
public interface UserDao extends BaseDao<User, String> {

    /**
     * 分页获取事件列表
     *
     * @param queryParameter 分页对象
     * @param params         查询条件  {EeventType:事件类型; eventStatus:实际状态; reportTime: 上报时间 }
     * @return
     * @author zhaohuan
     * @date 2017/2/22
     */
    Page<User> getUserList(QueryParameter queryParameter, Map<String, Object> params);

    void getUserByName(String name);

    List<User> getUserList();

}
