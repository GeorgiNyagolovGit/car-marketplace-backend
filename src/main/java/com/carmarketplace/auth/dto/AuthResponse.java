package com.carmarketplace.auth.dto;

public record AuthResponse(Long userId, String email, String firstName, String lastName) {
}
