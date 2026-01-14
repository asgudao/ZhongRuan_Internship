package com.tjetc.controller;

import com.tjetc.common.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//在返回数据给用户端之前，全局处理所有controller的方法异常，即所有的controller的方法出现异常都会被
//使用@RestControllerAdvice注解的类进行拦截统一处理
@RestControllerAdvice
@Slf4j
public class GlobalAdviceController {
    //处理Exception或者Exception子类异常，简言之处理所有的异常
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e) {
        e.printStackTrace();
        //记录日志
        //log.error("异常信息:", e.getCause());
        return JsonResult.fail("出错了，请联系管理员");
    }
}
