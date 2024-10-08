//package com.example.web.service.impl;
//
//import com.example.web.dto.RegisterDto;
////import com.example.web.enums.Role;
//import com.example.web.enums.Role;
//import com.example.web.models.UserEntity;
////import com.example.web.repository.RoleRepository;
//import com.example.web.repository.UserRepository;
//import com.example.web.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//import java.util.Arrays;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    private UserRepository userRepository;
////    private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;
////
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void saveUser(RegisterDto registrationDto) {
//        UserEntity user = new UserEntity();
//        user.setUsername(registrationDto.getUsername());
//        user.setEmail(registrationDto.getEmail());
//        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
////        Role role = roleRepository.findByName("Listener");
//        user.setRole(Role.USER);
//        userRepository.save(user);
//    }
//
//    @Override
//    public UserEntity findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public UserEntity findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//}
