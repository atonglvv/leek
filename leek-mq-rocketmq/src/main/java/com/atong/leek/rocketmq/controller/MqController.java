package com.atong.leek.rocketmq.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: leek
 * @description: message provider
 * @author: atong
 * @create: 2020-11-27 11:47
 */
@RestController
public class MqController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/hello")
    public String hello(){
        return "hello.world";
    }

    @GetMapping("/send")
    public String send() {
        rocketMQTemplate.syncSend("send", "Hello");
        return "send success..";
    }
}
