package com.example.test.CRUDWebAPI.JWTAuth.repositories;


import com.example.test.CRUDWebAPI.JWTAuth.models.Human;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HumanRepository extends JpaRepository<Human, Integer> {
    Optional<Human> findByUsername(String username);
}
