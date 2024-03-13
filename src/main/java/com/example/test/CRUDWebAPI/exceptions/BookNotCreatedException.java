package com.example.test.CRUDWebAPI.exceptions;

public class BookNotCreatedException extends RuntimeException{
    public BookNotCreatedException(String message){
        super(message);
    }
}
