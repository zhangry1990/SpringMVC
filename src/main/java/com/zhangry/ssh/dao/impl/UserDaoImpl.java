package com.zhangry.ssh.dao.impl;

import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.data.hibernate.HibernateDAO;
import com.zhangry.ssh.dao.UserDao;
import com.zhangry.ssh.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
@Repository
public class UserDaoImpl extends HibernateDAO<User, String> implements UserDao {
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
    public Page<User> getUserList(QueryParameter queryParameter, Map<String, Object> params) {
        //防止params为空
        if (params == null) {
            params = new HashMap<>();
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select id, name, sex, age, address from role where 1=1 ");
        if(params != null) {
            if (params.get("name") != null && StringUtils.isNotBlank(String.valueOf(params.get("name")))) {
                sqlBuilder.append(" and name >= :name");
            }
            if (params.get("sex") != null && StringUtils.isNotBlank(String.valueOf(params.get("sex")))) {
                sqlBuilder.append(" and sex >= :sex");
            }
            if (params.get("address") != null && StringUtils.isNotBlank(String.valueOf(params.get("address")))) {
                sqlBuilder.append(" and address >= :address");
            }
        }
        return this.findPageBySql(queryParameter, sqlBuilder.toString(), params);
    }
}
