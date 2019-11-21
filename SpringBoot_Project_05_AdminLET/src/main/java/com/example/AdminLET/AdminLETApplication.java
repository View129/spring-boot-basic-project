package com.example.AdminLET;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.example.AdminLET.mapper")
@SpringBootApplication
public class AdminLETApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminLETApplication.class, args);
    }

}
