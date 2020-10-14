package com.atong.leek.leekmq.producer;

import com.atong.leek.leekmq.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class Producer {
    @Autowired
    public RabbitTemplate rabbitTemplate;

    @GetMapping("/email")
    public String sendEmail() {
        String message = "send email";
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFO, "info.email", message);
        return "send success...";
    }

    /**
     * 对应Consume2
     * @return
     */
    @GetMapping("/hello")
    public String sendHello() {
        String message = "send hello";
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("hello", message);
        }
        return "send success...";
    }

    /**
     * 对应Consume2
     * Work模式
     * @return
     */
    @GetMapping("/work")
    public String sendWork() {
        String message = "send work";
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", message + i);
        }
        return "send success...";
    }

    /**
     * 对应Consume4
     * Fanout模式(广播)
     * @return
     */
    @GetMapping("/fanout")
    public String sendFanout() {
        String message = "send fanout";
        rabbitTemplate.convertAndSend("fanout", "", message);
        return "send success...";
    }

    /**
     * 对应Consume5
     * Route模式(路由)
     * @return
     */
    @GetMapping("/route")
    public String sendRoute() {
        String message = "send route";
        rabbitTemplate.convertAndSend("route", "error", message);
        return "send success...";
    }

    /**
     * 对应Consume6
     * Route模式(路由)
     * @return
     */
    @GetMapping("/topic")
    public String sendTopic() {
        String message = "send topic";
        rabbitTemplate.convertAndSend("topic", "order", message);
        return "send success...";
    }


    @GetMapping("/test")
    public String test() {
        return "hello world...";
    }


}
