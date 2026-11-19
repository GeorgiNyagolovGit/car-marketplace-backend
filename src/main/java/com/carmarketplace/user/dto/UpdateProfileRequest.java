package com.carmarketplace.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Schema(example = "Ivan")
        @NotBlank @Size(max = 80) String firstName,
        @Schema(example = "Petrov")
        @NotBlank @Size(max = 80) String lastName,
        @Schema(example = "+359 88 123 4567", nullable = true)
        @Size(max = 30)
        @Pattern(regexp = "^[0-9+() .-]*$", message = "phone contains unsupported characters")
        String phone) {
}
