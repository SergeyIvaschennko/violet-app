package com.example.web.service;

import com.example.web.dto.RegisterDto;
import com.example.web.models.UserEntity;

public interface UserService  {
    void saveUser(RegisterDto registrationDto);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
