package com.llye.mq.rabbitmq.producer.controller;

import com.llye.mq.rabbitmq.producer.dto.BookRequestDto;
import com.llye.mq.rabbitmq.producer.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody BookRequestDto bookRequestDto) {
        try {
            bookService.create(bookRequestDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("The request to process the book has been sent to the queue.", HttpStatus.ACCEPTED);
    }
}
