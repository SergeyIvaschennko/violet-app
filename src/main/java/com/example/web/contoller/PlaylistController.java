package com.example.web.contoller;

import com.example.web.dto.PlaylistDto;
import com.example.web.models.AudioFile;
import com.example.web.models.Playlist;
import com.example.web.models.UserEntity;
import com.example.web.repository.AudioRepository;
import com.example.web.repository.PlaylistRepository;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class PlaylistController {

    private AudioRepository audioRepository;

    private UserRepository userRepository;

    private PlaylistRepository playlistRepository;

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class);


    @Autowired
    public PlaylistController(AudioRepository audioRepository, UserRepository userRepository, PlaylistRepository playlistRepository) {
        this.audioRepository = audioRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
    }

    @PostMapping("/playlists")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistDto playlistDto){
        // Создание объекта плейлиста
        Playlist playlist = new Playlist();
        playlist.setName(playlistDto.getName());
        playlist.setPicUrl(playlistDto.getPicUrl());
        UserEntity userEntity = userRepository.findByUsername(playlistDto.getUsername());
        if (userEntity != null) {
            playlist.setUser(userEntity);
            playlistRepository.save(playlist);
            return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/audio/album/{albumName}")//получение аудио по альбому
    public ResponseEntity<List<Object[]>> getAudiosByAlbumName(@PathVariable String albumName) {

        List<Object[]> audios = audioRepository.findSongsByPlaylistId(playlistRepository.findIdByName(albumName));
        if (audios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(audios);
    }

    @DeleteMapping("/playlist/{playlistName}")
    public ResponseEntity<String> deletePlaylist(@PathVariable String playlistName) {
        List<AudioFile> audioFiles = audioRepository.findByPlaylistName(playlistName);

        for (AudioFile audioFile : audioFiles) {
            try {
                Path filePath = Paths.get("music", "audio", audioFile.getFilename());
                if (!Files.exists(filePath)) {
                    return ResponseEntity.notFound().build();
                }
                Files.delete(filePath);
                audioRepository.deleteByFilename(audioFile.getFilename());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        playlistRepository.deleteByPlaylistName(playlistName);
        return new ResponseEntity<>("Playlist deleted successfully", HttpStatus.OK);
    }



}
