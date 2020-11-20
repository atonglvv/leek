package com.atong.leek.leekmq.consumer;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello"))
public class Consumer2 {

    private static final Logger logger = LoggerFactory.getLogger(Consumer2.class);

    @RabbitHandler
    public void receive(String msg) {
        String messagemsg = StringEscapeUtils.escapeJava(msg);
        logger.info("consumer2 receive message is : {}", messagemsg);
    }

}
