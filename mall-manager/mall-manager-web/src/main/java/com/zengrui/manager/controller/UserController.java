package com.zengrui.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Zeng Rui on 2018/2/24.
 */
@Controller
public class UserController {
    @RequestMapping(value = "/login")
    public String login(){


        return "login";
        }



}
