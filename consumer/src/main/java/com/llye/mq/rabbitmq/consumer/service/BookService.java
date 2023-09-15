package com.llye.mq.rabbitmq.consumer.service;

import com.llye.mq.rabbitmq.consumer.entity.Book;
import com.llye.mq.rabbitmq.consumer.exception.ValidationException;
import com.llye.mq.rabbitmq.consumer.repository.BookRepository;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void insertBook(Book deserializedBook) throws ValidationException, DataIntegrityViolationException, PropertyValueException {
        if (Objects.isNull(deserializedBook)) {
            throw new ValidationException("deserializedBook must not be null.");
        }

        Book book = Book.builder()
                        .title(deserializedBook.getTitle())
                        .author(deserializedBook.getAuthor())
                        .genre(deserializedBook.getGenre())
                        .publishedYear(deserializedBook.getPublishedYear())
                        .build();
        bookRepository.save(book);
    }
}
