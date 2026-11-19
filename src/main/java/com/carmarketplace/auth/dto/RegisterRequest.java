package com.carmarketplace.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Schema(example = "new.user@example.com")
        @NotBlank @Email String email,
        @Schema(example = "Password123!", format = "password", minLength = 8, maxLength = 72)
        @NotBlank @Size(min = 8, max = 72) String password,
        @Schema(example = "Ivan")
        @NotBlank @Size(max = 80) String firstName,
        @Schema(example = "Petrov")
        @NotBlank @Size(max = 80) String lastName
) {
}
