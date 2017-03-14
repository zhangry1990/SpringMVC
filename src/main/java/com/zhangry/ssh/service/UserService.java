package com.zhangry.ssh.service;

import com.zhangry.common.page.QueryParameter;
import com.zhangry.ssh.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
public interface UserService extends BaseService<User, String> {
    /**
     * 分页获取事件列表
     *
     * @author zhaohuan
     * @date 2017/2/22
     * @param queryParameter 分页对象
     * @param params 查询条件  {EeventType:事件类型; eventStatus:实际状态; reportTime: 上报时间 }
     * @return
     */
    String getUserList(QueryParameter queryParameter, Map<String, Object> params);


    void getUserByName(String name);

    List<User> getUserList();

}
