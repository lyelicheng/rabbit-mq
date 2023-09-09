package com.llye.mq.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final MessageProducer messageProducer;

    public ProductService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void create(ProductRequestDto productRequestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(productRequestDto);
        messageProducer.send(jsonMessage);
    }
}
