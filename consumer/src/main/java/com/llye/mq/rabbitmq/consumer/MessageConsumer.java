package com.llye.mq.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receive(String message) {
        LOG.info("Received message: {}", message);
    }
}
