package com.atong.leek.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String hello() {
        logger.error("localhost:8090/hello is running...");
        return "Hello World...";
    }

    @GetMapping("/throwName")
    public String throwName() {
        String throwName = null;
        try {
            int a = 1/0;
        } catch (Exception e) {
            throwName = e.getClass().getName();
        }
        return throwName;
    }
}
