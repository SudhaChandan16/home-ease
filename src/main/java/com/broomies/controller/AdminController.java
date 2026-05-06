package com.broomies.controller;

import com.broomies.service.ProviderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProviderService providerService;

    public AdminController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        return ResponseEntity.ok(Map.of("providers", providerService.getAllProviders()));
    }
}
