package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public JsonResult login(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        return adminService.login(username, password);
    }
}
