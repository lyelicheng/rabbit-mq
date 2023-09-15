package com.llye.mq.rabbitmq.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private String title;
    private String author;
    private String genre;
    private Integer publishedYear;
}
