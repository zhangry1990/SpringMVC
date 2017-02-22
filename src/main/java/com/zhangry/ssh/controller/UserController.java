package com.zhangry.ssh.controller;

import com.zhangry.json.JsonView;
import com.zhangry.ssh.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
@Controller
public class UserController extends BaseController {

    @RequestMapping(value = "/eventList", method = RequestMethod.GET)
    public String eventList() {
        return "event/eventList";
    }

    @RequestMapping(value = "/eventDetail", method = RequestMethod.GET)
    public String eventDetail() {
        return "event/eventDetail";
    }
    //获取事件采集的基础数据
    @RequestMapping(value = "/eventBase")
    public JsonView getEventCreateBase() {
        //当前用户

        return new JsonView();
    }
}
