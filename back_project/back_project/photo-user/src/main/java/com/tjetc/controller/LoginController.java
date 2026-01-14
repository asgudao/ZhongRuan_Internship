package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public JsonResult login(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        return userService.login(username, password);
    }
}
