package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.User;
import com.tjetc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public JsonResult list() {
        return userService.findAll();
    }

    @RequestMapping("add")
    public JsonResult add(User user) {
        return userService.add(user);
    }

    @RequestMapping("page")
    public JsonResult page(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "username", required = false, defaultValue = "") String username) {
        return userService.findPage(pageNo, pageSize, username);
    }
    @RequestMapping("check-exist")
    public JsonResult checkExist(@RequestParam("username")String username){
        return userService.checkExistByUsername(username);
    }

    @RequestMapping("detail/{id}")
    public JsonResult detail(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @RequestMapping("update")
    public JsonResult update(User user)
    {
        return userService.updateById(user);
    }

    @GetMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return userService.deleteById(id);
    }
}
