package com.example.web.contoller;

import com.example.web.dto.AudioDto;
import com.example.web.models.AudioFile;
import com.example.web.models.UserEntity;
import com.example.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;

@CrossOrigin("http://localhost:3000")
@RestController
public class AudioController {

    private AudioRepository audioRepository;
    private GenreRepository genreRepository;
    private VibeRepository vibeRepository;
    private UserRepository userRepository;
    private PlaylistRepository playlistRepository;


    @Autowired
    public AudioController(AudioRepository audioRepository, GenreRepository genreRepository, VibeRepository vibeRepository, UserRepository userRepository, PlaylistRepository playlistRepository) {
        this.audioRepository = audioRepository;
        this.genreRepository = genreRepository;
        this.vibeRepository = vibeRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("/")
    public String indexx() {
        return "index";
    }

    @GetMapping("/uploadAudio")
    public String uploadAudio() {
        return "uploadAudio";
    }


    // Ваш метод в контроллере для загрузки файла
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, AudioDto audioDto, RedirectAttributes redirectAttributes) {
        try {
            String uniqueFileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            if (audioRepository.existsByName(uniqueFileName)) { //присвоение нового имени, если файл с таким именем уже существует
                int numb = audioRepository.findIdByName(uniqueFileName);
                int extensionIndex = uniqueFileName.lastIndexOf('.');
                String nameWithoutExtension = uniqueFileName.substring(0, extensionIndex);
                String extension = uniqueFileName.substring(extensionIndex);
                uniqueFileName = nameWithoutExtension + numb + extension;
            }

            AudioFile audioFile = new AudioFile();

            audioFile.setFilename(uniqueFileName);
            audioFile.setName(audioDto.getName());
            audioFile.setDate(new Date());
            audioFile.setTime(audioDto.getTime());
            audioFile.setUser(userRepository.findByUsername(audioDto.getArtist_name()));
            audioFile.setGenre(genreRepository.findGenreByName(audioDto.getGenre()));
            audioFile.setVibe(vibeRepository.findVibeByName(audioDto.getVibe()));
            audioFile.setPlaylist(playlistRepository.findPlaylistByName(audioDto.getPlaylist_name()));

            audioRepository.save(audioFile);
            Path path = Paths.get("music/audio/" + uniqueFileName);
            // Переносим файл в папку ./music/audio/
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + uniqueFileName + "!");
        } catch (Exception e) {
            // Обработка ошибок загрузки файла
            redirectAttributes.addFlashAttribute("error",
                    "Failed to upload file. " + e.getMessage());
        }
        return "alright";
    }



    @GetMapping("/find/{username}")
    public UserEntity getRegisterForm(@PathVariable String username) {
        return userRepository.findByUsername(username);
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
            long contentLength = Files.size(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setContentLength(contentLength);
            headers.set("Accept-Ranges", "bytes");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Если произошла ошибка, вернуть 500 Internal Server Error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/audio/{filename}")
    public ResponseEntity<?> deleteAudioFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("music", "audio", filename);
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }
            Files.delete(filePath);
            audioRepository.deleteByFilename(filename);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/audio/genre/{genrename}")
    public List<AudioFile> getAudioFilesByGenreName(@PathVariable String genrename) {
        return audioRepository.findByGenreName(genrename);
    }

}

