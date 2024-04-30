package com.example.web.repository;

import com.example.web.models.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AudioRepository extends JpaRepository<AudioFile, Long> {
//    Optional<Club> findByTitle(String url);
    @Transactional
    @Modifying
    @Query("DELETE FROM AudioFile a WHERE a.filename = ?1")
    void deleteByFilename(String filename);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM AudioFile u WHERE u.filename = :filename")
    boolean existsByName(@Param("filename") String filename);

    @Query("SELECT u.id FROM AudioFile u WHERE u.filename = :filename")
    int findIdByName(@Param("filename") String filename);
}
