package com.zhangry.ssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangry on 2017/2/22.
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping("/signin")
    public String signInPage() {
        return "signin";
    }

    @RequestMapping("/signout")
    public String signOutPage() {
        return "signout";
    }
}
