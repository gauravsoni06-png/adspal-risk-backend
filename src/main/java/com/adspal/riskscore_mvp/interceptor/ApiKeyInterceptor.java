package com.adspal.riskscore_mvp.interceptor;

import com.adspal.riskscore_mvp.entity.Brand;
import com.adspal.riskscore_mvp.repository.BrandRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@CrossOrigin(origins = "*")
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Brand brand = brandRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        request.setAttribute("brand", brand);
        return true;
    }
}
