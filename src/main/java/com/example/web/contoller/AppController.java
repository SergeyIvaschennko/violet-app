package com.example.web.contoller;

import com.example.web.models.*;
import com.example.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class AppController {

    private AudioRepository audioRepository;

    private UserRepository userRepository;

    private MetaUserRepository metaUserRepository;

    private PlaylistRepository playlistRepository;

    private GenreRepository genreRepository;


    @Autowired
    public AppController(AudioRepository audioRepository, UserRepository userRepository, MetaUserRepository metaUserRepository, PlaylistRepository playlistRepository, GenreRepository genreRepository) {
        this.audioRepository = audioRepository;
        this.userRepository = userRepository;
        this.metaUserRepository = metaUserRepository;
        this.playlistRepository = playlistRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/all-artists")//все артисты
    public List<Object[]> getAllArtists() {
        return metaUserRepository.findAllMetaUserInfo();
    }

    @GetMapping("/all-genres")//получение всех песен конкретного артиста
    public List<Genre> getAllGenre() {
        return genreRepository.findAll();
    }


    @GetMapping("/latest")//5 самых поздних песенок
    public List<Object[]> getLatestAudioFiles() {
        List<Object[]> latestFiles = new ArrayList<>();

        // Получаем список 4 аудиофайлов с разными альбомами
        Pageable pageable = PageRequest.of(0, 5);
        List<Object[]> filesWithDifferentAlbums = audioRepository.findTop5ByDate(pageable);

        return filesWithDifferentAlbums;
    }

//    @GetMapping("/artists/{userId}")//страница артиста, где будет шапка и лого
//    public ResponseEntity<MetaUser> getMetaUserById(@PathVariable Long userId) {
//        MetaUser metaUser = metaUserRepository.findMetaUserByUserId(userId);
//        if (metaUser != null) {
//            return ResponseEntity.ok(metaUser);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/playlist/{username}")//список плейлистов у конкретного артиста
    public ResponseEntity<List<Playlist>> getPlaylistsByUserId(@PathVariable String username) {
        List<Playlist> playlists = playlistRepository.findAllByUserId(userRepository.findUserByUsername(username).getId());
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/playlist/music/{playlistName}") //музыка в конкретном альбоме
    public List<AudioFile> getAudioFilesByPlaylistId(@PathVariable String playlistName) {
        return audioRepository.findByPlaylistName(playlistName);
    }

    @GetMapping("/user/{userId}")//получение всех песен конкретного артиста
    public ResponseEntity<List<AudioFile>> getAudioFilesByUserId(@PathVariable Long userId) {
        List<AudioFile> audioFiles = audioRepository.findByUserId(userId);
        return ResponseEntity.ok(audioFiles);
    }

    @GetMapping("/genre/{genrename}")
    public Genre getGenreByGenreName(@PathVariable String genrename) {
        return genreRepository.findGenreByName(genrename);
    }





}
