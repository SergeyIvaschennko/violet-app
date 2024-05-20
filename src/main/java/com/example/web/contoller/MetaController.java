package com.example.web.contoller;

import com.example.web.dto.MetaDto;
import com.example.web.dto.PlaylistDto;
import com.example.web.models.*;
import com.example.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class MetaController {

    private UserRepository userRepository;

    private MetaUserRepository metaUserRepository;



    @Autowired
    public MetaController(UserRepository userRepository, MetaUserRepository metaUserRepository) {
        this.userRepository = userRepository;
        this.metaUserRepository = metaUserRepository;
    }

    @PostMapping("/meta")
    public ResponseEntity<MetaUser> createMeta(@RequestBody MetaDto metaDto){
        MetaUser metaUser = new MetaUser();
        metaUser.setUsername(metaDto.getUsername());
        metaUser.setPicUrl(metaDto.getPicUrl());
        metaUser.setPicCoverUrl(metaDto.getPicCoverUrl());
        metaUserRepository.save(metaUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON) // Установка Content-Type
                .body(metaUser);
//        return ResponseEntity.status(HttpStatus.CREATED).body(metaUser);
    }

    @GetMapping("/meta/{username}")
    public ResponseEntity<MetaUser> getMeta(@PathVariable String username) {
        MetaUser metaUser = metaUserRepository.findMetaUserByUsername(username);
        if (metaUser != null) {
            return ResponseEntity.ok(metaUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}