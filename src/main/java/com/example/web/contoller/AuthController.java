package com.example.web.contoller;

import com.example.web.dto.RegisterDto;
import com.example.web.models.UserEntity;
import com.example.web.repository.UserRepository;
import com.example.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {
//    private UserService userService;
//    private UserRepository userRepository;
//
//    public AuthController(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping("/login")
//    public String loginPage(){
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String getRegisterForm(Model model) {
//        RegisterDto user = new RegisterDto();
//        model.addAttribute("user", user);
//        return "register";
//    }
//
//    @PostMapping("/register/save")
//    public String register(@Valid @ModelAttribute("user")RegisterDto user,
//                           BindingResult result, Model model) {
//        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
//        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
//            return "redirect:/register?fail";
//        }
//        UserEntity existingUserUsername = userService.findByUsername(user.getUsername());
//        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
//            return "redirect:/products?fail";
//        }
//        if(result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "register";
//        }
//        userService.saveUser(user);
//        return "redirect:/";
//    }
}
