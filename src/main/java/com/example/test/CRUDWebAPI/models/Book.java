package com.example.test.CRUDWebAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "isbn")
    @Min(value = 0, message = "Should not be less than 0;")
    @NotNull(message = "Should not be null")
    private int isbn;
    @Column(name = "name")
    @NotEmpty(message = "Should not be empty")
    private String name;

    @Column(name = "genre")
    @NotEmpty(message = "Should not be empty")
    private String genre;

    @Column(name = "description")
    @NotEmpty(message = "Should not be empty")
    private String description;

    @Column(name = "author")
    @NotEmpty(message = "Should not be empty")
    private String author;

    public Book(String name, int isbn, String genre, String description, String author) {
        this.name = name;
        this.isbn = isbn;
        this.genre = genre;
        this.description = description;
        this.author = author;
    }
    public Book() {
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}