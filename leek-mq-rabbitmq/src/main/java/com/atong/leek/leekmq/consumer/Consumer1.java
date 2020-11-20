package com.atong.leek.leekmq.consumer;

import com.atong.leek.leekmq.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Consumer1 {

    private static final Logger logger = LoggerFactory.getLogger(Consumer1.class);

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void consumer(String msg, Message message, Channel channel) {
        System.out.println("receive message is : " + msg);
        logger.info("consumer1 receive message is {}", msg);
    }
}
