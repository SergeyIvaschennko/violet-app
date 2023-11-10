package com.example.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterDto {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
