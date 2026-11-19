package com.carmarketplace.car.service;

import com.carmarketplace.car.dto.CarFilterRequest;
import com.carmarketplace.car.dto.CarRequest;
import com.carmarketplace.car.dto.CarResponse;
import com.carmarketplace.car.entity.Car;
import com.carmarketplace.car.repository.CarRepository;
import com.carmarketplace.common.exception.NotFoundException;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional(readOnly = true)
    public List<CarResponse> findAll(CarFilterRequest filter) {
        return carRepository.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.brandId() != null) predicates.add(builder.equal(root.get("brandId"), filter.brandId()));
            if (filter.minPrice() != null) predicates.add(builder.greaterThanOrEqualTo(root.get("price"), filter.minPrice()));
            if (filter.maxPrice() != null) predicates.add(builder.lessThanOrEqualTo(root.get("price"), filter.maxPrice()));
            if (filter.minYear() != null) predicates.add(builder.greaterThanOrEqualTo(root.get("year"), filter.minYear()));
            if (filter.maxYear() != null) predicates.add(builder.lessThanOrEqualTo(root.get("year"), filter.maxYear()));
            if (filter.fuelType() != null) predicates.add(builder.equal(root.get("fuelType"), filter.fuelType()));
            if (filter.transmission() != null) predicates.add(builder.equal(root.get("transmission"), filter.transmission()));
            return builder.and(predicates.toArray(Predicate[]::new));
        }).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CarResponse findById(Long id) {
        return toResponse(getCar(id));
    }

    @Transactional
    public CarResponse create(CarRequest request) {
        Car car = new Car();
        applyRequest(car, request);
        return toResponse(carRepository.save(car));
    }

    @Transactional
    public CarResponse update(Long id, CarRequest request) {
        Car car = getCar(id);
        applyRequest(car, request);
        return toResponse(carRepository.save(car));
    }

    @Transactional
    public void delete(Long id) {
        carRepository.delete(getCar(id));
    }

    private Car getCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car with id " + id + " was not found"));
    }

    private void applyRequest(Car car, CarRequest request) {
        car.setTitle(request.title().trim());
        car.setBrandId(request.brandId());
        car.setModelId(request.modelId());
        car.setYear(request.year());
        car.setPrice(request.price());
        car.setFuelType(request.fuelType());
        car.setTransmission(request.transmission());
    }

    private CarResponse toResponse(Car car) {
        return new CarResponse(car.getId(), car.getTitle(), car.getPrice(), car.getStatus());
    }
}
