package com.carmarketplace.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateBrandRequest(
        @Schema(example = "Test Brand")
        @NotBlank @Size(max = 100) String name,
        @Schema(description = "Optional models for the new brand")
        List<@Valid CreateCarModelRequest> models) {
}
