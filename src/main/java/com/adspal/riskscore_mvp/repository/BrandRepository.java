package com.adspal.riskscore_mvp.repository;

import com.adspal.riskscore_mvp.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    Optional<Brand> findByApiKey(String apiKey);
}
