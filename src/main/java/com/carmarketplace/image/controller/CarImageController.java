package com.carmarketplace.image.controller;

import com.carmarketplace.image.dto.CarImageResponse;
import com.carmarketplace.image.dto.CreateCarImageRequest;
import com.carmarketplace.image.service.CarImageService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars/{carId}/images")
public class CarImageController {
    private final CarImageService carImageService;

    public CarImageController(CarImageService carImageService) {
        this.carImageService = carImageService;
    }

    @GetMapping
    public List<CarImageResponse> findAll(@PathVariable Long carId) {
        return carImageService.findAllByCarId(carId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarImageResponse create(@PathVariable Long carId,
                                   @Valid @RequestBody CreateCarImageRequest request) {
        return carImageService.create(carId, request);
    }

    @PatchMapping("/{imageId}/primary")
    public CarImageResponse setPrimary(@PathVariable Long carId, @PathVariable Long imageId) {
        return carImageService.setPrimary(carId, imageId);
    }

    @DeleteMapping("/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long carId, @PathVariable Long imageId) {
        carImageService.delete(carId, imageId);
    }
}
