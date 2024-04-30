package com.example.web.contoller;

import com.example.web.models.AudioFile;
import com.example.web.models.Bookmark;
import com.example.web.repository.AudioRepository;
import com.example.web.repository.BookmarkRepository;
import com.example.web.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BookmarkController {

    private BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkController(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;

    }

    @GetMapping("/bookmark/{id}")
    public List<AudioFile> index(@PathVariable Long id) {
        return bookmarkRepository.findAllByUserId(id);
    }


}