package com.llye.mq.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llye.mq.rabbitmq.consumer.entity.Book;
import com.llye.mq.rabbitmq.consumer.exception.ValidationException;
import com.llye.mq.rabbitmq.consumer.service.BookService;
import com.llye.mq.rabbitmq.consumer.util.DateUtil;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class MessageConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);
    private static final int RABBIT_MQ_MAX_RETRY_COUNT = 3;
    private static final long RABBIT_MQ_DELAYED_MESSAGE_MS = 5_000;

    private final BookService bookService;
    private final RabbitTemplate rabbitTemplate;
    private final String pocMQ;
    private final String pocMQDlq;

    public MessageConsumer(BookService bookService,
                           RabbitTemplate rabbitTemplate,
                           @Value("${spring.rabbitmq.queue}") String pocMQ,
                           @Value("${spring.rabbitmq.queue.dlq}") String pocMQDlq) {
        this.bookService = bookService;
        this.rabbitTemplate = rabbitTemplate;
        this.pocMQ = pocMQ;
        this.pocMQDlq = pocMQDlq;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receive(Message message) {
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        LOG.info("Received message: {}", messageBody);

        ObjectMapper objectMapper = new ObjectMapper();
        Book deserializedBook = null;
        try {
            deserializedBook = objectMapper.readValue(messageBody, Book.class);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to deserialize message from MQ: {}", e.toString());
        }

        try {
            bookService.insertBook(deserializedBook);
        } catch (ValidationException | DataIntegrityViolationException | PropertyValueException e) {
            LOG.error("Error while inserting a book to DB: {}", e.toString());
            handleExceptionMessage(message);
        }
    }

    private void handleExceptionMessage(Message message) {
        MessageProperties messageProperties = message.getMessageProperties();

        Integer retryCount = (Integer) messageProperties.getHeaders().getOrDefault("x-retry-count", 0);
        if (retryCount < RABBIT_MQ_MAX_RETRY_COUNT) {
            retryCount++;
            messageProperties.setHeader("x-retry-count", retryCount);
            messageProperties.setHeader("x-delayed-message", RABBIT_MQ_DELAYED_MESSAGE_MS); // Delay before retrying (milliseconds)
            rabbitTemplate.send(pocMQ, message);
        } else {
            messageProperties.setHeader("dlq-timestamp", DateUtil.formatToISO8601(new Date()));
            rabbitTemplate.send(pocMQDlq, message);
        }
    }
}
