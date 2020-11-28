package com.atong.leek.rocketmq.consumer;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @program: leek
 * @description: rocketConsume
 * @author: atong
 * @create: 2020-11-27 15:44
 */
@Component
@RocketMQMessageListener(   consumerGroup = "sendGroup",
                            topic = "send",
                            consumeMode = ConsumeMode.CONCURRENTLY,//非顺序消费-并行
                            messageModel = MessageModel.CLUSTERING,//集群消费
                            consumeTimeout  = 60000L,//超时时间
                            consumeThreadMax = 10)
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }
}
