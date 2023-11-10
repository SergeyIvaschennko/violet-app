package com.example.web.dto;

import com.example.web.models.UserEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Product title should not be empty")
    private String title;
    @NotEmpty(message = "Photo link should not be empty")
    private String photoUrl;
    @NotNull(message = "Amount should not be empty")
    private Long amount;
    @NotNull(message = "Price should not be empty")
    private UserEntity createdBy;
    private Long price;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
