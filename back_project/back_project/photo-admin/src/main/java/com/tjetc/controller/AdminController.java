package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Admin;
import com.tjetc.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("list")
    public JsonResult list() {
        return adminService.findAll();
    }

    @RequestMapping("add")
    public JsonResult add(Admin admin) {
        return adminService.add(admin);
    }

    @RequestMapping("page")
    public JsonResult page(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "username", required = false, defaultValue = "") String username) {
        return adminService.findPage(pageNo, pageSize, username);
    }
    @RequestMapping("check-exist")
    public JsonResult checkExist(@RequestParam("username")String username){
        return adminService.checkExistByUsername(username);
    }

    @RequestMapping("detail/{id}")
    public JsonResult detail(@PathVariable("id") Integer id) {
        return adminService.findById(id);
    }

    @RequestMapping("update")
    public JsonResult update(@RequestBody Admin admin)
    {
        return adminService.updateById(admin);
    }

    @GetMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return adminService.deleteById(id);
    }
}
