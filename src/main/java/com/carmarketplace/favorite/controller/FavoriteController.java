package com.carmarketplace.favorite.controller;

import com.carmarketplace.favorite.dto.FavoriteResponse;
import com.carmarketplace.favorite.service.FavoriteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public List<FavoriteResponse> findAll(Authentication authentication) {
        return favoriteService.findAll(authentication.getName());
    }

    @PostMapping("/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteResponse create(@PathVariable Long carId, Authentication authentication) {
        return favoriteService.create(authentication.getName(), carId);
    }

    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long carId, Authentication authentication) {
        favoriteService.delete(authentication.getName(), carId);
    }
}
