package com.example.web.repository;

import com.example.web.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findGenreByName(String name);
}
