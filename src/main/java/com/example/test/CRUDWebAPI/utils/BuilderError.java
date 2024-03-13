package com.example.test.CRUDWebAPI.utils;


import com.example.test.CRUDWebAPI.exceptions.BookNotCreatedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
public class BuilderError {
    public void handleBindingErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new BookNotCreatedException(errorMessage.toString());
        }
    }
}

