package com.example.web.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetaDto {
    private String picUrl;
    private String picCoverUrl;
    private String username;
}
