package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import com.tjetc.entity.Question;
import com.tjetc.entity.User;
import com.tjetc.service.QuestionService;
import com.tjetc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("question")
@Slf4j
public class UserQuestionController {
    @Autowired
    private QuestionService questionService;


    @RequestMapping("add")
    public JsonResult add(Question question) {
        return questionService.add(question);
    }

}
