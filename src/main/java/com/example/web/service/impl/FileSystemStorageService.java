package com.example.web.service.impl;

import com.example.web.models.AudioFile;
import com.example.web.repository.AudioRepository;
import com.example.web.repository.RoleRepository;
import com.example.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import com.example.web.service.StorageService;

@Service
public class FileSystemStorageService implements StorageService {

    private AudioRepository audioRepository;

    @Autowired
    public FileSystemStorageService(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }


//    public void store(MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            Path directoryPath = Paths.get("src/main/resources/static/audio");
//            Path filePath = directoryPath.resolve(fileName);
//            Files.createDirectories(directoryPath);
//            file.transferTo(filePath.toFile());
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store file", e);
//        }
//    }



    @Override
    public List<AudioFile> getAllAudioFiles() {
        return audioRepository.findAll();
    }

}


