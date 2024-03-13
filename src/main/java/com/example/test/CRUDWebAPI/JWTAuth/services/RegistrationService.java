package com.example.test.CRUDWebAPI.JWTAuth.services;

import com.example.test.CRUDWebAPI.JWTAuth.models.Human;
import com.example.test.CRUDWebAPI.JWTAuth.repositories.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

    private final HumanRepository humanRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(HumanRepository humanRepository, PasswordEncoder passwordEncoder) {
        this.humanRepository = humanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Human human) {
        human.setPassword(passwordEncoder.encode(human.getPassword()));
        humanRepository.save(human);
    }
}
