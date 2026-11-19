package com.carmarketplace.user.controller;

import com.carmarketplace.user.dto.UpdateProfileRequest;
import com.carmarketplace.user.dto.UserResponse;
import com.carmarketplace.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}/profile")
    public UserResponse updateProfile(@PathVariable Long id, @Valid @RequestBody UpdateProfileRequest request) {
        return userService.updateProfile(id, request);
    }
}
