package com.example.test.CRUDWebAPI.JWTAuth.utils;

import com.example.test.CRUDWebAPI.JWTAuth.models.Human;
import com.example.test.CRUDWebAPI.JWTAuth.services.HumanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class HumanValidator implements Validator {

    private final HumanDetailsService humanDetailsService;

    @Autowired
    public HumanValidator(HumanDetailsService humanDetailsService) {
        this.humanDetailsService=humanDetailsService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Human.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Human human = (Human) target;

        try {
            humanDetailsService.loadUserByUsername(human.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "..");
    }
}
