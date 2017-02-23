/*
 * Copyright (C) 2017 南京思创信息技术有限公司
 * <p>
 * 版权所有。
 * <p>
 * 功能概要    :
 * 做成日期    : 2017/2/10
 */
package com.zhangry.ssh.service;

import com.alibaba.fastjson.JSONObject;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.ssh.entity.Role;

import java.util.Map;

/**
 * 角色接口
 * @author anss
 * @date 2017/2/10.
 */
public interface RoleService extends BaseService<Role, String> {

    /**
     * 分页查询角色列表
     * @param queryParameter
     * @param condition
     * @param columns
     * @return
     */
    String getRoleSPageByCondition(QueryParameter queryParameter, Map<String, Object> condition, String... columns);


    /**
     * 保存角色(添加/修改)
     * @author anss
     * @date 2017/2/10
     * @param role
     */
    void save(Role role);

    /**
     * 根据角色主键Id获取角色实体
     * @author anss
     * @date 2017/2/10
     * @param id
     * @return
     */
    String getRole(String id);

    /**
     * 判断角色名称是否存在
     * @author anss
     * @date 2017/2/13
     * @param roleName
     * @return ture（已存在）或false（不存在）
     */
    boolean isExistRoleName(String roleName);

    /**
     * 根据条件删除角色（支持批量逻辑删除）
     * @author anss
     * @date 2017/2/13
     * @param condition{idS:多个之间以“，”逗号分割角色Id集合；user:用户实体}
     * @return {true:删除成功；false：删除失败（存在未被解除的级联关系）}
     */
    boolean deleteRole(Map<String, Object> condition);

    /**
     * 给用户赋角色
     * @author anss
     * @date 2017/2/13
     * @param condition{roleId：角色id； userIdS：多个之间以“，”逗号分割用户id集合}
     */
    void saveUserRole(Map<String, Object> condition);

    /**
     * 给角色赋权限（给角色添加资源）
     * @author anss
     * @date 2017/2/13
     * @param condition{roleId：角色id； resourceIdS：多个之间以“，”逗号分割资源id集合}
     */
    void saveRoleResource(Map<String, Object> condition);

    /**
     * 根据条件获取已启用的角色列表（不分页）
     * @author anss
     * @date 2017/2/10
     * @param condition
     * @param columns
     * @return
     */
    String getRoleList(Map<String, Object> condition, String... columns);

}
