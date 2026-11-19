package com.carmarketplace.admin.service;

import com.carmarketplace.admin.dto.AdminDashboardResponse;
import com.carmarketplace.car.dto.CarFilterRequest;
import com.carmarketplace.car.dto.CarRequest;
import com.carmarketplace.car.dto.CarResponse;
import com.carmarketplace.car.repository.CarRepository;
import com.carmarketplace.car.service.CarService;
import com.carmarketplace.common.exception.NotFoundException;
import com.carmarketplace.user.dto.UserResponse;
import com.carmarketplace.user.dto.UpdateProfileRequest;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarService carService;

    public AdminService(UserRepository userRepository, CarRepository carRepository,
                        CarService carService) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.carService = carService;
    }

    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {
        return new AdminDashboardResponse(userRepository.count(), carRepository.count());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(this::toUserResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findUserById(Long id) {
        return toUserResponse(getUser(id));
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateProfileRequest request) {
        User user = getUser(id);
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());
        user.setPhone(request.phone() == null || request.phone().isBlank() ? null : request.phone().trim());
        return toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(getUser(id));
    }

    @Transactional(readOnly = true)
    public List<CarResponse> findAllCars(CarFilterRequest filter) {
        return carService.findAll(filter);
    }

    @Transactional(readOnly = true)
    public CarResponse findCarById(Long id) {
        return carService.findById(id);
    }

    @Transactional
    public CarResponse createCar(CarRequest request) {
        return carService.create(request);
    }

    @Transactional
    public CarResponse updateCar(Long id, CarRequest request) {
        return carService.update(id, request);
    }

    @Transactional
    public void deleteCar(Long id) {
        carService.delete(id);
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getPhone());
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " was not found"));
    }
}
