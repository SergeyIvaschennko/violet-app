package com.example.web.contoller;

import com.example.web.models.AudioFile;
import com.example.web.repository.AudioRepository;
import com.example.web.service.StorageService;
import com.example.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;


@Controller
public class AudioController {

    private final StorageService storageService;
    private AudioRepository audioRepository;

    @Autowired
    public AudioController(StorageService storageService, AudioRepository audioRepository) {
        this.storageService = storageService;
        this.audioRepository = audioRepository;

    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/uploadAudio")
    public String uploadAudio() {
        return "uploadAudio";
    }

    // Ваш метод в контроллере для загрузки файла
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            String fileName = file.getOriginalFilename();
            String uniqueFileName = fileName;
            byte[] bytes = file.getBytes();

            if (audioRepository.existsByName(fileName)) {
                int numb = audioRepository.findIdByName(fileName);
                int extensionIndex = fileName.lastIndexOf('.');
                String nameWithoutExtension = fileName.substring(0, extensionIndex);
                String extension = fileName.substring(extensionIndex);
                uniqueFileName = nameWithoutExtension + numb + extension;
            }

            AudioFile audioFile = AudioFile.builder()
                    .filename(uniqueFileName)
                    .build();
            audioRepository.save(audioFile);
            Path path = Paths.get("music/audio/" + uniqueFileName);
            // Переносим файл в папку ./music/audio/
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + fileName + "!");
        } catch (Exception e) {
            // Обработка ошибок загрузки файла
            redirectAttributes.addFlashAttribute("error",
                    "Failed to upload file. " + e.getMessage());
        }
        return "redirect:/";
    }



    @GetMapping("/audio/files")
    @ResponseBody
    public List<AudioFile> listAudioFiles() {
        return audioRepository.findAll();
    }

    @GetMapping("/audio/{filename}")
    public ResponseEntity<InputStreamResource> getAudioFile(@PathVariable String filename) {
        try {
            // Получаем путь к файлу в директории static/audio
            Path filePath = Paths.get("music", "audio", filename);
            // Проверяем, существует ли файл
            if (!Files.exists(filePath)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            InputStream inputStream = new FileSystemResource("music/audio/" + filename).getInputStream();
            // Создаем ресурс из потока для отправки в ответе
            InputStreamResource resource = new InputStreamResource(inputStream);
            // Определяем MIME-тип для аудиофайла
            MediaType mediaType = MediaType.parseMediaType("audio/mpeg");
            // Возвращаем ResponseEntity с аудиофайлом и заголовками
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            // Если произошла ошибка, вернуть 500 Internal Server Error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/audio/{filename}")
    public ResponseEntity<?> deleteAudioFile(@PathVariable String filename) {
        try {
            // Получаем путь к файлу в директории static/audio
            Path filePath = Paths.get("music", "audio", filename);
            // Проверяем, существует ли файл
            if (!Files.exists(filePath)) {
                // Если файл не существует, возвращаем 404 Not Found
                return ResponseEntity.notFound().build();
            }
            // Удаляем файл
            Files.delete(filePath);
            // Удаляем запись из базы данных
            audioRepository.deleteByFilename(filename);
            // Возвращаем успешный ответ
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            // Если произошла ошибка ввода-вывода, возвращаем 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}

