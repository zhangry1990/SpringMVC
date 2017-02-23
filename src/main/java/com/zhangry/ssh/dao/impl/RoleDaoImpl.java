/**
 * Copyright (C) 2017 南京思创信息技术有限公司
 * <p>
 * 版权所有。
 * <p>
 * 功能概要    :
 * 做成日期    : 2017/1/16
 */
package com.zhangry.ssh.dao.impl;

import com.zhangry.common.constant.Constant;
import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.common.util.StringUtil;
import com.zhangry.data.hibernate.HibernateDAO;
import com.zhangry.ssh.dao.RoleDao;
import com.zhangry.ssh.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色DAO实现
 * @author anss
 * @date 2017/2/13
 */
@Repository
public class RoleDaoImpl extends HibernateDAO<Role, String> implements RoleDao {

    //HQL共通语句
    private static final String HQL = " FROM Role role WHERE role.deletedFlag = " + Constant.DELETEDFLAG_NO;

    /**
     * 分页查询角色列表
     * @author anss
     * @date 2017/2/10
     * @param queryParameter
     * @param condition
     * @param columns
     * @return
     */
    @Override
    public Page<Role> getRolesPage(QueryParameter queryParameter, Map<String, Object> condition, String... columns) {
        //角色名称
        String strRoleName = (String) condition.get("roleName");
        //启用状态（0-禁用；1-启用）
        String strEnabled = (String) condition.get("enabled");
        //备注
        String strRemarks = (String) condition.get("remarks");

        StringBuffer hql = new StringBuffer(HQL);
        // 查询列表集合
        Map<String, Object> paramS = new HashMap<>();

        //判断角色名称是否为空
        if (!StringUtil.isNullOrEmpty(strRoleName)) {
            strRoleName = StringUtil.encodeSqlLike(strRoleName.trim(),"/");
            hql.append(" AND role.roleName LIKE :roleName");
            paramS.put("roleName", "%" + strRoleName + "%");
        }
        //判断启用状态是否为空
        if (!StringUtil.isNullOrEmpty(strEnabled)) {
            hql.append(" AND role.enabled = :enabled");
            paramS.put("enabled", Integer.valueOf(strEnabled));
        }
        //判断备注是否为空
        if (!StringUtil.isNullOrEmpty(strRemarks)) {
            strRemarks = StringUtil.encodeSqlLike(strRemarks.trim(),"/");
            hql.append(" AND role.remarks LIKE :remarks");
            paramS.put("remarks", "%" + strRemarks + "%");
        }

        hql.append(" ORDER BY role.seq ASC, role.createdTime DESC");
        return this.findPage(queryParameter, hql.toString(), paramS);
    }

    /**
     * 判断角色名称是否存在
     * @author anss
     * @date 2017/2/10
     * @param roleName:角色名称
     * @return ture（已存在）或false（不存在）
     */
    @Override
    public boolean isExistRoleName(String roleName) {
        Map<String, Object> paramS = new HashMap<>(1);
        StringBuffer hql = new StringBuffer(HQL);
        hql.append(" AND role.roleName =:roleName");
        paramS.put("roleName", roleName);
        List<Role> lstRole = find(hql, paramS);
        //如果lstRole > 0，说明已经存在
        return lstRole.size() > 0;
    }

    /**
     * 根据条件获取角色实体list
     * @author anss
     * @date 2017/2/10
     * @param condition
     * @return
     */
    @Override
    public List<Role> getRoleList(Map<String, Object> condition) {
        //角色名称
        String strRoleName = (String) condition.get("roleName");

        Map<String, Object> paramS = new HashMap<>(1);
        StringBuffer hql = new StringBuffer(HQL);
        //判断角色名称是否为空
        if (!StringUtil.isNullOrEmpty(strRoleName)) {
            hql.append(" AND role.roleName =:roleName");
            paramS.put("roleName", strRoleName);
        }
        hql.append(" AND role.enabled = " + Constant.ENABLED);
        hql.append(" ORDER BY role.seq ASC, role.createdTime DESC");
        return find(hql, paramS);

    }
}
