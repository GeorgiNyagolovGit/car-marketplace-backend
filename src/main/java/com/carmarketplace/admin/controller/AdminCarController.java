package com.carmarketplace.admin.controller;

import com.carmarketplace.admin.service.AdminService;
import com.carmarketplace.car.dto.CarFilterRequest;
import com.carmarketplace.car.dto.CarRequest;
import com.carmarketplace.car.dto.CarResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/admin/cars")
public class AdminCarController {
    private final AdminService adminService;

    public AdminCarController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<CarResponse> findAll(@ModelAttribute CarFilterRequest filter) {
        return adminService.findAllCars(filter);
    }

    @GetMapping("/{id}")
    public CarResponse findById(@PathVariable Long id) {
        return adminService.findCarById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse create(@Valid @RequestBody CarRequest request) {
        return adminService.createCar(request);
    }

    @PutMapping("/{id}")
    public CarResponse update(@PathVariable Long id, @Valid @RequestBody CarRequest request) {
        return adminService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        adminService.deleteCar(id);
    }
}
