package com.example.web.repository;

import com.example.web.models.Genre;
import com.example.web.models.UserEntity;
import com.example.web.models.Vibe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VibeRepository extends JpaRepository<Vibe, Long> {
    Vibe findVibeByName(String name);
}
