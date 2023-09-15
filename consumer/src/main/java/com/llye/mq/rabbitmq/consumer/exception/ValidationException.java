package com.llye.mq.rabbitmq.consumer.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class ValidationException extends IllegalArgumentException {
    String errorMessage;

    public ValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
