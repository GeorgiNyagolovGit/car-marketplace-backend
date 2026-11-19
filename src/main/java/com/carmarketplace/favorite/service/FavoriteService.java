package com.carmarketplace.favorite.service;

import com.carmarketplace.car.service.CarService;
import com.carmarketplace.common.exception.NotFoundException;
import com.carmarketplace.favorite.dto.FavoriteResponse;
import com.carmarketplace.favorite.entity.Favorite;
import com.carmarketplace.favorite.repository.FavoriteRepository;
import com.carmarketplace.user.entity.User;
import com.carmarketplace.user.repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final CarService carService;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository,
                           CarService carService) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.carService = carService;
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> findAll(String email) {
        Long userId = getUserId(email);
        return favoriteRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public FavoriteResponse create(String email, Long carId) {
        Long userId = getUserId(email);
        if (favoriteRepository.findByUserIdAndCarId(userId, carId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Car is already in favorites");
        }

        carService.findById(carId);
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setCarId(carId);
        return toResponse(favoriteRepository.save(favorite));
    }

    @Transactional
    public void delete(String email, Long carId) {
        Favorite favorite = favoriteRepository.findByUserIdAndCarId(getUserId(email), carId)
                .orElseThrow(() -> new NotFoundException("Favorite for car with id " + carId + " was not found"));
        favoriteRepository.delete(favorite);
    }

    private Long getUserId(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(User::getId)
                .orElseThrow(() -> new NotFoundException("Current user was not found"));
    }

    private FavoriteResponse toResponse(Favorite favorite) {
        return new FavoriteResponse(favorite.getId(), carService.findById(favorite.getCarId()),
                favorite.getCreatedAt());
    }
}
