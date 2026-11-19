package com.carmarketplace.image.service;

import com.carmarketplace.car.repository.CarRepository;
import com.carmarketplace.common.exception.NotFoundException;
import com.carmarketplace.image.dto.CarImageResponse;
import com.carmarketplace.image.dto.CreateCarImageRequest;
import com.carmarketplace.image.entity.CarImage;
import com.carmarketplace.image.repository.CarImageRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarImageService {
    private final CarImageRepository carImageRepository;
    private final CarRepository carRepository;

    public CarImageService(CarImageRepository carImageRepository, CarRepository carRepository) {
        this.carImageRepository = carImageRepository;
        this.carRepository = carRepository;
    }

    @Transactional(readOnly = true)
    public List<CarImageResponse> findAllByCarId(Long carId) {
        ensureCarExists(carId);
        return carImageRepository.findAllByCarIdOrderByPrimaryDescIdAsc(carId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CarImageResponse create(Long carId, CreateCarImageRequest request) {
        ensureCarExists(carId);
        if (request.primary()) {
            clearPrimaryImage(carId);
        }

        CarImage image = new CarImage();
        image.setCarId(carId);
        image.setUrl(request.url().trim());
        image.setPrimary(request.primary());
        return toResponse(carImageRepository.save(image));
    }

    @Transactional
    public CarImageResponse setPrimary(Long carId, Long imageId) {
        clearPrimaryImage(carId);
        CarImage image = getImage(carId, imageId);
        image.setPrimary(true);
        return toResponse(carImageRepository.save(image));
    }

    @Transactional
    public void delete(Long carId, Long imageId) {
        carImageRepository.delete(getImage(carId, imageId));
    }

    private void clearPrimaryImage(Long carId) {
        carImageRepository.findAllByCarIdAndPrimaryTrue(carId)
                .forEach(image -> image.setPrimary(false));
    }

    private CarImage getImage(Long carId, Long imageId) {
        return carImageRepository.findByIdAndCarId(imageId, carId)
                .orElseThrow(() -> new NotFoundException("Image with id " + imageId + " was not found"));
    }

    private void ensureCarExists(Long carId) {
        if (!carRepository.existsById(carId)) {
            throw new NotFoundException("Car with id " + carId + " was not found");
        }
    }

    private CarImageResponse toResponse(CarImage image) {
        return new CarImageResponse(image.getId(), image.getUrl(), image.isPrimary());
    }
}
