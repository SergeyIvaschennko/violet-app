package com.example.web.contoller;

import com.example.web.models.AudioFile;
import com.example.web.models.Bookmark;
import com.example.web.models.UserEntity;
import com.example.web.repository.AudioRepository;
import com.example.web.repository.BookmarkRepository;
import com.example.web.repository.UserRepository;
import com.example.web.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class BookmarkController {

    private BookmarkRepository bookmarkRepository;

    private UserRepository userRepository;

    private AudioRepository audioRepository;

    @Autowired
    public BookmarkController(BookmarkRepository bookmarkRepository, UserRepository userRepository, AudioRepository audioRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.audioRepository = audioRepository;


    }

    @GetMapping("/bookmarks/{username}")//закладки конкретного юзера
    public List<AudioFile> UsersBookmark(@PathVariable String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return bookmarkRepository.findAllByUserId(userEntity.getId());
    }

    @PostMapping("/bookmarks/{username}/{audioname}/{artistname}")
    public ResponseEntity<String> createBookmark(@PathVariable String username, @PathVariable String audioname, @PathVariable String artistname) {
        AudioFile audioFile = audioRepository.findByNameAndUserUsername(audioname, artistname);
        UserEntity userEntity = userRepository.findByUsername(username);

        if (audioFile != null && userEntity != null) {
            Bookmark bookmark = new Bookmark();
            bookmark.setAudio(audioFile);
            bookmark.setUserEntity(userEntity);

            bookmarkRepository.save(bookmark);

            return ResponseEntity.ok("Bookmark created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid audioId or userId.");
        }
    }

    @GetMapping("/bookmarks/check/{username}/{audioname}")//смотрим есть ли саундтрек в любимых
    public boolean checkBookmark(@PathVariable String username, @PathVariable String audioname) {
        return bookmarkRepository.existsByAudioIdAndUserId(audioname, username);
    }

    @DeleteMapping("/bookmarks/{username}/{audioname}")
    public ResponseEntity<String> deleteBookmark(@PathVariable String username, @PathVariable String audioname) {
        try {
            UserEntity userEntity = userRepository.findByUsername(username);
            AudioFile audioFile = audioRepository.findByName(audioname);
            bookmarkRepository.deleteByUserEntityIdAndAudioId(userEntity.getId(), audioFile.getId());
            return ResponseEntity.ok("Закладка успешно удалена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Что-то пошло не так при удалении закладки");
        }
    }




}