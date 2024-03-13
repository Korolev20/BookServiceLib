package com.example.test.CRUDWebAPI.repositories;

import com.example.test.CRUDWebAPI.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List <Book> findByIsbn(int isbn);

}