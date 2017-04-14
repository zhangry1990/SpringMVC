package com.zhangry.ssh.controller;

import com.zhangry.ssh.service.RoleService;
import com.zhangry.ssh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/23.
 */
@Controller
@RequestMapping(value = "/roles")
public class RoleController extends BaseController {

    //用户service
    @Autowired
    private UserService userService;
    //资源service
   /*@Autowired
    private ResourceService resourceService;*/
    //角色service
    @Autowired
    private RoleService roleservice;


    /**
     * 角色管理
     * @author anss
     * @date 2017/2/16
     * @return
     */
    @RequestMapping
    public ModelAndView roles() {
        ModelAndView modelAndView = new ModelAndView("roles");
        return modelAndView;
    }

    /**
     * 获取角色管理列表
     * @author anss
     * @date 2017/2/16
     * @return
     * @RequestParam(value = "roleName", required = false) String roleName
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public void getRoleList(@RequestBody Map<String, String> params) {
        Map<String, Object> condition = new HashMap<String, Object>();
        //角色名称
        condition.put("name", params.get("name"));
        //启用状态（0-禁用；1-启用）
        condition.put("sex", params.get("sex"));
        //备注
        condition.put("address", params.get("address"));
        String[] columns = {"id", "name", "sex", "age", "address"};
        String result = roleservice.getRoleSPageByCondition(getQueryParameter(params), condition, columns);

        render(result.replace("totalrecords", "total"));
    }

    /**
     * 新增角色
     * @author anss
     * @date 2017/2/16
     * @param params
     */
    /*@RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody Map<String, String> params) {

        Role role = new Role();
        role.setCreatedTime(new Date());
        role.setCreatedUserId(userId());
        role.setCreatedUserName(userFullName());

        //角色名称
        role.setRoleName(params.get("roleName"));
        //排序
        role.setSeq(Integer.parseInt(params.get("seq")));
        //是否启用
        role.setEnabled(Integer.parseInt(params.get("enabled")));
        //备注
        role.setRemarks(params.get("remarks"));

        roleservice.save(role);
        render(String.valueOf(true));
    }*/

    /**
     * 修改角色
     * @author anss
     * @date 2017/2/16
     */
    /*@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable("id") String roleId, @RequestBody Map<String, String> params) {
        Role newRole = new Role();

        String oldRole = roleservice.getRole(roleId);
        JSONObject roleObj = JSON.parseObject(oldRole);

        newRole.setId(roleObj.getString("id"));
        newRole.setCreatedTime(roleObj.getDate("createdTime"));
        newRole.setCreatedUserId(roleObj.getString("createdUserId"));
        newRole.setCreatedUserName(roleObj.getString("createdUserName"));
        newRole.setModifiedTime(new Date());
        newRole.setModifiedUserId(userId());
        newRole.setModifiedUserName(userFullName());

        //角色名称
        newRole.setRoleName(params.get("roleName"));
        //排序
        newRole.setSeq(Integer.parseInt(params.get("seq")));
        //是否启用
        newRole.setEnabled(Integer.parseInt(params.get("enabled")));
        //备注
        newRole.setRemarks(params.get("remarks"));

        roleservice.save(newRole);
        render(String.valueOf(true));
    }
*/
    /**
     * 检验角色名称是否重复
     * @author anss
     * @date 2017/2/16
     * ture（已存在）或false（不存在）
     */
   /* @RequestMapping(value = "/roleName", method = RequestMethod.GET)
    public void checkRoleName(@RequestParam("roleName") String roleName) {
        boolean isExists = roleservice.isExistRoleName(roleName);
        if (isExists) {
            render("false");
        } else {
            render("true");
        }
    }
*/
    /**
     * 根据角色主键Id获取实体
     * @author anss
     * @date 2017/2/16
     * @return Role
     */
   /* @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void getRole(@PathVariable("id") String roleId) {
        String roleArray = roleservice.getRole(roleId);
        render(roleArray);
    }*/

    /**
     * 删除角色
     * @author anss
     * @date 2017/2/16
     * @param roleIdS
     */
    /*@RequestMapping(value = "/{roleIdS}", method = RequestMethod.DELETE)
    public void deleteRole(@PathVariable(value = "roleIdS", required = false) String roleIdS) {
        Map<String, Object> condition = new HashMap<>();
        //角色IdS集合
        condition.put("idS", roleIdS);
        //当前登录用户
        condition.put("user", currentUser());
        //delFlag:删除标识（true:删除成功；false：删除失败（存在未被解除的级联关系））
        boolean delFlag = roleservice.deleteRole(condition);
        render(String.valueOf(delFlag));
    }*/

    /**
     * 用户tree
     * @author anss
     * @date 2017/2/20
   /*  *//*
    @RequestMapping("/userTree")
    public void getUserTree() {
        String userTree = userService.getUserTree();
        render(userTree.replace("NOCHECK", "nocheck"));
    }*/

    /**
     * 给用户赋角色
     * @author anss
     * @date 2017/2/20
     * {roleId：角色id； userIdS：多个之间以“，”逗号分割用户id集合}
     */
   /* @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    public void saveUserRole(@RequestBody Map<String, Object> params) {
        roleservice.saveUserRole(params);
        render(String.valueOf(true));
    }*/

    /**
     * 资源tree
     * @author anss
     * @date 2017/2/21
     */
    /*@RequestMapping("/resourceTree")
    public void getResourceTree() {
        Map<String, Object> params = new HashMap<>();
        String[] columns = {"id", "resourceName", "pResource.id"};
        String resourceTree = resourceService.getResourceTree(params, columns);
        render(resourceTree);
    }*/

    /**
     * 给角色赋权限（给角色添加资源）
     * @author anss
     * @date 2017/2/13
     * @param {roleId：角色id； resourceIdS：多个之间以“，”逗号分割资源id集合}
     */
   /* @RequestMapping(value = "/roleResource", method = RequestMethod.POST)
    public void saveRoleResource(@RequestBody Map<String, Object> params) {
        roleservice.saveRoleResource(params);
        render(String.valueOf(true));
    }*/
}
