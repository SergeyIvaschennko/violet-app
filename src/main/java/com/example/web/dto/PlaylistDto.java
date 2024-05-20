package com.example.web.dto;

import com.example.web.models.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistDto {
    private String name;
    private String picUrl;
    private String username;
}
