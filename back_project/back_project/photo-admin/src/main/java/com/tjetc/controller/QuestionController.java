package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.service.AdminService;
import com.tjetc.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return questionService.deleteById(id);
    }

    @RequestMapping("list")
    public JsonResult list() {
        return questionService.findAll();
    }

    @RequestMapping("page")
    public JsonResult page(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "username", required = false, defaultValue = "") String username) {
        return questionService.findPage(pageNo, pageSize, username);
    }
}
