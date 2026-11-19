package com.carmarketplace.image.repository;

import com.carmarketplace.image.entity.CarImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    List<CarImage> findAllByCarIdOrderByPrimaryDescIdAsc(Long carId);

    Optional<CarImage> findByIdAndCarId(Long id, Long carId);

    List<CarImage> findAllByCarIdAndPrimaryTrue(Long carId);
}
