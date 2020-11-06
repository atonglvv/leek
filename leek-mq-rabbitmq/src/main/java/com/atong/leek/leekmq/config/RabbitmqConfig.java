package com.atong.leek.leekmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description  rabbitmq配置类
 * @author atong
 * @date 14:53 2020/10/30
 * @version 1.0.0.1
 **/
@Configuration
public class RabbitmqConfig {
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static String EXCHANGE_TOPICS_INFO = "exchange_topics_info";
    public static String ROUTINGKEY_EMAIL = "inform.#.email.#";

    //声明队列
    @Bean
    public Queue queueEmail() {
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    //声明Topic交换机
    @Bean
    Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFO).durable(true).build();
    }

    //将队列与Topic交换机进行绑定，并指定路由键
    @Bean
    Binding topicBindingEmail(@Qualifier("queueEmail") Queue queue, @Qualifier("topicExchange")Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
    }
}
