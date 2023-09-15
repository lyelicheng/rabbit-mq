package com.llye.mq.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProducer.class);

    private final Queue queue;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(@Value("${spring.rabbitmq.queue}") Queue queue, RabbitTemplate rabbitTemplate) {
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(queue.getName(), message);
        LOG.info("Sent message: {}", message);
    }
}
