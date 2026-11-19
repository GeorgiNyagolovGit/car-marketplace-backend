package com.carmarketplace.car.dto;

import com.carmarketplace.car.enums.FuelType;
import com.carmarketplace.car.enums.Transmission;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarRequest(
        @Schema(example = "BMW 330i Test")
        @NotBlank String title,
        @Schema(example = "1")
        @NotNull Long brandId,
        @Schema(example = "1", nullable = true)
        Long modelId,
        @Schema(example = "2024", minimum = "1886", maximum = "2100")
        @NotNull @Min(1886) @Max(2100) Integer year,
        @Schema(example = "45000.00", minimum = "0.01")
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @Schema(example = "PETROL")
        @NotNull FuelType fuelType,
        @Schema(example = "AUTOMATIC")
        @NotNull Transmission transmission) {
}
