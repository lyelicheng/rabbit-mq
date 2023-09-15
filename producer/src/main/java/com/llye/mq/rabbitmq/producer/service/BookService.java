package com.llye.mq.rabbitmq.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llye.mq.rabbitmq.producer.MessageProducer;
import com.llye.mq.rabbitmq.producer.dto.BookRequestDto;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final MessageProducer messageProducer;

    public BookService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void create(BookRequestDto bookRequestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(bookRequestDto);
        messageProducer.send(jsonMessage);
    }
}
