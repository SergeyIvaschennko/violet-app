package com.example.web.repository;

import com.example.web.models.AudioFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface AudioRepository extends JpaRepository<AudioFile, Long> {
//    Optional<Club> findByTitle(String url);

    List<AudioFile> findByGenreId(Long genreId);

    List<AudioFile> findByGenreName(String genreName);

    @Query("SELECT a.name AS name, a.filename AS filename, a.user.username AS username, p.picUrl AS playlistPicUrl, a.time AS time, p.name AS playlistName " +
            "FROM AudioFile a " +
            "JOIN a.playlist p " +
            "ORDER BY a.date DESC")
    List<Object[]> findTop5ByDate(Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM AudioFile a WHERE a.filename = ?1")
    void deleteByFilename(String filename);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM AudioFile u WHERE u.filename = :filename")
    boolean existsByName(@Param("filename") String filename);

    @Query("SELECT u.id FROM AudioFile u WHERE u.filename = :filename")
    int findIdByName(@Param("filename") String filename);

    @Query("SELECT af.name, af.time FROM AudioFile af JOIN af.playlist p WHERE p.id = :playlistId")
    List<Object[]> findSongsByPlaylistId(Long playlistId);

    List<AudioFile> findByPlaylistId(Long playlistId);

    List<AudioFile> findByUserId(Long userId);

    List<AudioFile> findByPlaylistName(String Name);

    AudioFile findByName(String name);

    @Query("SELECT a FROM AudioFile a WHERE a.name = :name AND a.user.username = :username")
    AudioFile findByNameAndUserUsername(@Param("name") String name, @Param("username") String username);
}

