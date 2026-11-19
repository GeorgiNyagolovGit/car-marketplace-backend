package com.carmarketplace.car.dto;

import com.carmarketplace.car.enums.CarStatus;
import java.math.BigDecimal;

public record CarResponse(Long id, String title, BigDecimal price, CarStatus status) {
}
