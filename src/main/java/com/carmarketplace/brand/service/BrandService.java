package com.carmarketplace.brand.service;

import com.carmarketplace.brand.dto.BrandResponse;
import com.carmarketplace.brand.dto.CreateBrandRequest;
import com.carmarketplace.brand.entity.Brand;
import com.carmarketplace.brand.entity.CarModel;
import com.carmarketplace.brand.repository.BrandRepository;
import com.carmarketplace.common.exception.NotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BrandResponse findById(Long id) {
        return toResponse(getBrand(id));
    }

    @Transactional
    public BrandResponse create(CreateBrandRequest request) {
        String name = request.name().trim();
        if (brandRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Brand with name " + name + " already exists");
        }

        Brand brand = new Brand();
        brand.setName(name);
        if (request.models() != null) {
            request.models().forEach(modelRequest -> {
                CarModel model = new CarModel();
                model.setName(modelRequest.name().trim());
                brand.addModel(model);
            });
        }
        return toResponse(brandRepository.save(brand));
    }

    private Brand getBrand(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand with id " + id + " was not found"));
    }

    private BrandResponse toResponse(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName(),
                brand.getModels().stream().map(CarModel::getName).toList());
    }
}
