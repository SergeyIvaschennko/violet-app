package com.example.web.service;

import com.example.web.models.AudioFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {
    List<AudioFile> getAllAudioFiles();

//    void store(MultipartFile file);



}
