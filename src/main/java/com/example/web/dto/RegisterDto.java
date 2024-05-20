package com.example.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


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
