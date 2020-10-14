package com.atong.leek.leekmq.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queuesToDeclare = @Queue(value = "hello"))
public class Consumer2 {

    @RabbitHandler
    public void receive(String msg) {
        System.out.println("message = " + msg);
    }

}
