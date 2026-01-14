package com.tjetc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjetc.common.JsonResult;
import com.tjetc.common.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 登录拦截器，校验用户是否登录或者登录是否过期
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    //springboot默认转换json的工具对象（Jackson第三方包的）
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //设置响应的数据类型为json
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        //获取token
        String token = request.getHeader("token");
        //没有token或者token过期、错误等返回false 不让继续执行controller方法
        if (StringUtils.isNotBlank(token)) {
            //校验token是否过期、错误，是的就是拦截，否则就放行
            try {
                Claims claims = JwtTokenUtil.parseJwt(token);
                //放行
                return true;
            } catch (Exception e) {
                //说明token有问题 拦截不放行
                writeJson(response);
                return false;
            }
        } else {
            //拦截不放行
            writeJson(response);
            return false;
        }
    }

    private void writeJson(HttpServletResponse response) throws IOException {
        JsonResult jsonResult = JsonResult.fail(-1, "未登录或者登录已过期");
        //手动把jsonResult转换成json字符串
        String json = objectMapper.writeValueAsString(jsonResult);
        //把json字符串输出到前端
        response.getWriter().write(json);
    }
}
