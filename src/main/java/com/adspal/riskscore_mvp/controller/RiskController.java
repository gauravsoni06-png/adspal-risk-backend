package com.adspal.riskscore_mvp.controller;

import com.adspal.riskscore_mvp.entity.Brand;
import com.adspal.riskscore_mvp.entity.RiskEvent;
import com.adspal.riskscore_mvp.repository.BrandRepository;
import com.adspal.riskscore_mvp.repository.RiskEventRepository;
import com.adspal.riskscore_mvp.service.RiskScoringService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/risk")
@CrossOrigin(origins = "*")
public class RiskController {

    private final RiskEventRepository repo;
    private final RiskScoringService service;
    private final BrandRepository brandRepository;
    public RiskController(RiskEventRepository repo, RiskScoringService service, BrandRepository brandRepository) {
        this.repo = repo;
        this.service = service;
        this.brandRepository = brandRepository;
    }

    @PostMapping("/score")
    public RiskEvent score(@RequestBody RiskEvent event, HttpServletRequest request) {

        Brand brand = (Brand) request.getAttribute("brand");
        System.out.println("Brand Name while saving " +brand.getName());
        RiskEvent req = service.calculateRisk(brand, event);
        return repo.save(req);
    }

    @GetMapping("/recent")
    public List<RiskEvent> recent(@RequestHeader("X-API-KEY") String apiKey) {
        Brand brand = brandRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API key"));

        System.out.println("Brand Name:" +brand.getName().toString());
        return repo
                .findTop10ByBrandOrderByCreatedAtDesc(brand);
    }
}
