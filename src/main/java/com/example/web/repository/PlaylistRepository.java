package com.example.web.repository;

import com.example.web.models.AudioFile;
import com.example.web.models.MetaUser;
import com.example.web.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAllByUserId(Long UserId);

    @Query("SELECT p.id FROM Playlist p WHERE p.name = :playlistName")
    Long findIdByName(@Param("playlistName") String playlistName);

    Playlist findPlaylistByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Playlist a WHERE a.name = :playlistName")
    void deleteByPlaylistName(String playlistName);
}

