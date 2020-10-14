package com.atong.leek.leekmq.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer3 {

    @RabbitListener(queuesToDeclare = @Queue(value = "work"))
    public void revice1(String msg) {
        System.out.println("message1 = " + msg);
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "work"))
    public void revice2(String msg) {
        System.out.println("message2 = " + msg);
    }
}
