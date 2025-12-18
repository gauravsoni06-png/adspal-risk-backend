package com.adspal.riskscore_mvp.repository;

import com.adspal.riskscore_mvp.entity.Brand;
import com.adspal.riskscore_mvp.entity.RiskEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskEventRepository extends JpaRepository<RiskEvent, Long> {
    List<RiskEvent> findTop10ByBrandOrderByCreatedAtDesc(Brand brand);
    List<RiskEvent> findByBrand(Brand brand);

}
