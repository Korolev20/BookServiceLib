package com.example.test.CRUDWebAPI.JWTAuth.services;

import com.example.test.CRUDWebAPI.JWTAuth.security.HumanDetails;
import com.example.test.CRUDWebAPI.JWTAuth.models.Human;

import com.example.test.CRUDWebAPI.JWTAuth.repositories.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HumanDetailsService implements UserDetailsService {
    private final HumanRepository humanRepository;

    @Autowired
    public HumanDetailsService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Human> human = humanRepository.findByUsername(username);

        if (human.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new HumanDetails(human.get());
    }
}
