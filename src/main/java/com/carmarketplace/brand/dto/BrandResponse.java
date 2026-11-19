package com.carmarketplace.brand.dto;

import java.util.List;

public record BrandResponse(Long id, String name, List<String> models) {
}
