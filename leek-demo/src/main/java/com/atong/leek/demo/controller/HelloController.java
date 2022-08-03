package com.atong.leek.demo.controller;

import com.atong.leek.demo.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("hello")
    public User hello() {
        logger.error("localhost:8090/hello is running...{}", "a");
        User user = new User();
        user.setName(null);
//        user.setAge(18);
        user.setBrithday(LocalDateTime.now());
        return user;
    }
}
