package com.atong.leek.leekmq.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer4 {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, //创建临时队列
                    exchange = @Exchange(value = "fanout", type = "fanout") //绑定的交换机
            )
    })
    public void receive1(String msg) {
        System.out.println("messsge1 = " + msg);
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, //创建临时队列
                    exchange = @Exchange(value = "fanout", type = "fanout") //绑定的交换机
            )
    })
    public void receive2(String msg) {
        System.out.println("messsge2 = " + msg);
    }
}
