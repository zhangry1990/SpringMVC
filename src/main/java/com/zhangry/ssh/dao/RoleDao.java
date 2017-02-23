/*
 * Copyright (C) 2017 南京思创信息技术有限公司
 * <p>
 * 版权所有。
 * <p>
 * 功能概要    :
 * 做成日期    : 2017/2/10
 */
package com.zhangry.ssh.dao;


import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.ssh.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色DAO
 * @author anss
 * @date 2017/1/16.
 */
public interface RoleDao extends BaseDao<Role, String> {

    /**
     * 分页查询角色列表
     * @author anss
     * @date 2017/2/10
     * @param queryParameter
     * @param condition
     * @param columns
     * @return
     */
    Page<Role> getRolesPage(QueryParameter queryParameter, Map<String, Object> condition, String... columns);

    /**
     * 判断角色名称是否存在
     * @author anss
     * @date 2017/2/10
     * @param roleName
     * @return
     */
    boolean isExistRoleName(String roleName);

    /**
     * 根据条件获取角色实体list
     * @author anss
     * @date 2017/2/10
     * @param condition
     * @return
     */
    List<Role> getRoleList(Map<String, Object> condition);

}
