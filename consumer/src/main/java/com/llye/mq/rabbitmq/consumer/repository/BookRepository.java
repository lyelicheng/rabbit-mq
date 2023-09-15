package com.llye.mq.rabbitmq.consumer.repository;

import com.llye.mq.rabbitmq.consumer.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
