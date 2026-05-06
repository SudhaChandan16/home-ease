package com.broomies.controller;

import com.broomies.entity.Provider;
import com.broomies.enums.ProviderCategory;
import com.broomies.service.ProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final ProviderService providerService;

    public PublicController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/providers")
    public ResponseEntity<List<Provider>> listProviders(
            @RequestParam(required = false) ProviderCategory category,
            @RequestParam(required = false) String city) {
        
        List<Provider> providers;
        if (category != null || city != null) {
            providers = providerService.filterProviders(category, city);
        } else {
            providers = providerService.getAllProviders();
        }
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/providers/search")
    public ResponseEntity<List<Provider>> searchProviders(@RequestParam(required = false) String keyword) {
        List<Provider> providers;
        if (keyword != null && !keyword.isEmpty()) {
            providers = providerService.searchProviders(keyword);
        } else {
            providers = providerService.getAllProviders();
        }
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/providers/{id}")
    public ResponseEntity<?> viewProfile(@PathVariable @NonNull Long id) {
        try {
            Provider provider = providerService.getProviderById(id);
            return ResponseEntity.ok(provider);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
