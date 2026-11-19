package com.carmarketplace.car.dto;

import com.carmarketplace.car.enums.FuelType;
import com.carmarketplace.car.enums.Transmission;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record CarFilterRequest(
        @Schema(example = "1") Long brandId,
        @Schema(example = "20000") BigDecimal minPrice,
        @Schema(example = "70000") BigDecimal maxPrice,
        @Schema(example = "2018") Integer minYear,
        @Schema(example = "2025") Integer maxYear,
        @Schema(example = "DIESEL") FuelType fuelType,
        @Schema(example = "AUTOMATIC") Transmission transmission) {
}
