package com.example.test.CRUDWebAPI.services;

import com.example.test.CRUDWebAPI.models.Book;
import com.example.test.CRUDWebAPI.repositories.BookRepository;
import com.example.test.CRUDWebAPI.exceptions.BookNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository peopleRepository) {
        this.bookRepository = peopleRepository;
    }

    public List<Book> findAll() {
        List<Book> foundBook = bookRepository.findAll();
        if (foundBook.isEmpty()) {
            throw new BookNotFoundException();
        } else {
            return foundBook;
        }
    }
    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElseThrow(BookNotFoundException::new);
    }
    public List<Book> findByIsbn(int isbn) {
        List<Book> foundBook = bookRepository.findByIsbn(isbn);
        if (foundBook.isEmpty()) {
            throw new BookNotFoundException();
        } else {
            return foundBook;
        }
    }

    public void deleteOne(int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    public void updateBook(int id,Book book) {
        book.setId(id);
        bookRepository.save(book);
    }
}