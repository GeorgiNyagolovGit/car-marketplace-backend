package com.carmarketplace.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCarModelRequest(
        @Schema(example = "Test Model")
        @NotBlank @Size(max = 100) String name) {
}
