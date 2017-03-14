package com.zhangry.ssh.controller;

import com.zhangry.common.page.QueryParameter;
import com.zhangry.json.JsonView;
import com.zhangry.ssh.entity.User;
import com.zhangry.ssh.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    //add by zhangying 20170223 start
    //事件类型常量
    //养护
    private static final String EVENT_TYPE_YH = "";
    //路政
    private static final String EVENT_TYPE_LZ = "";
    //路网
    private static final String EVENT_TYPE_LW = "";
    //add by zhangying 20170223 end

    /**
     * 获取事件列表页面
     * @return
     * @author lifang
     * @date 20170221
     */
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String eventList() {
        return "user/userList";
    }

    /**
     * 分页获取事件列表
     * @param params Map<String, Object> 含分页信息的参数集
     * @return
     * @author lifang
     * @date 20170222
     */
    @RequestMapping(value = "/gridTable", method = RequestMethod.POST)
    /*public JsonView getGridData(Map<String, Object> params) {
//        Map<String, Object> data = new HashedMap();
        QueryParameter query = new QueryParameter();
        query.setPageNo(Integer.parseInt(params.get("pageNumber").toString()));
        query.setPageSize(Integer.parseInt(params.get("pageSize").toString()));
        String result = userService.getUserList(query, params);
        return new JsonView(result);
    }*/
    public JsonView getGridData(@RequestBody Map<String, Object> params) {
        QueryParameter query = new QueryParameter();
        String result = null;
        try {
            query.setPageNo(Integer.parseInt(params.get("pageNumber").toString()));
            query.setPageSize(Integer.parseInt(params.get("pageSize").toString()));
            result = userService.getUserList(query, params);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }

        return new JsonView(result);
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public void getUserList(@RequestBody User user) {
        System.out.println(user.getName());
        System.out.println(user.getAge());
        List<User> userList = userService.getUserList();
        System.out.println(userList.size());
    }
}
