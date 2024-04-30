package com.example.web.repository;

import com.example.web.models.AudioFile;
import com.example.web.models.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("SELECT a FROM AudioFile a INNER JOIN Bookmark b ON a.id = b.audio.id WHERE b.userEntity.id = ?1")
    List<AudioFile> findAllByUserId(Long userId);

}
