package com.atong.leek.imagetemp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.atong.leek.imagetemp.mapper")
@SpringBootApplication
public class ImageTempApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageTempApplication.class, args);
    }

}
