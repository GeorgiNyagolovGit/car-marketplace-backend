package com.carmarketplace.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCarImageRequest(
        @Schema(example = "https://example.com/car.jpg")
        @NotBlank
        @Size(max = 2048)
        @Pattern(regexp = "^https?://.+", message = "url must start with http:// or https://")
        String url,
        @Schema(example = "true")
        boolean primary) {
}
