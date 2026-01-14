package com.tjetc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.tjetc.dao")
public class PhotoUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhotoUserApplication.class, args);
    }
}
