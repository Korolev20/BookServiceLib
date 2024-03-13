package com.example.test.CRUDWebAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class BookDTO {

    @Min(value = 0, message = "Should not be less than 0;")
    @NotNull(message = "Should not be null")

    private int isbn;

    @NotEmpty(message = "Should not be empty")
    private String name;


    @NotEmpty(message = "Should not be empty")
    private String genre;


    @NotEmpty(message = "Should not be empty")
    private String description;

    @NotEmpty(message = "Should not be empty")
    private String author;

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
}
