package com.tjetc.config;

import com.tjetc.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //对所有的url请求都要使用loginInterceptor拦截器
                .addPathPatterns("/**")
                //排除哪些url请求不使用loginInterceptor拦截器
                .excludePathPatterns("/login","/user/add", "/image/**", "/video/**", "/**/*.js", "/**/*.css");
    }
}
