package com.carmarketplace.favorite.dto;

import com.carmarketplace.car.dto.CarResponse;
import java.time.Instant;

public record FavoriteResponse(Long id, CarResponse car, Instant createdAt) {
}
