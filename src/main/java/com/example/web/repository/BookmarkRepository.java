package com.example.web.repository;

import com.example.web.models.AudioFile;
import com.example.web.models.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.awt.print.Book;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("SELECT a FROM AudioFile a INNER JOIN Bookmark b ON a.id = b.audio.id WHERE b.userEntity.id = ?1")
    List<AudioFile> findAllByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Bookmark b " +
            "WHERE b.audio.name = :audioname AND b.userEntity.username = :username")
    boolean existsByAudioIdAndUserId(@Param("audioname") String audioname, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM Bookmark b WHERE b.userEntity.id = :userId AND b.audio.id = :audioId")
    void deleteByUserEntityIdAndAudioId(@Param("userId") Long userId, @Param("audioId") Long audioId);


}
