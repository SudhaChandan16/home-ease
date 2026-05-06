package com.broomies.service;

import com.broomies.entity.Provider;
import com.broomies.enums.ProviderCategory;
import com.broomies.repository.ProviderRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public List<Provider> findByCategory(ProviderCategory category) {
        return providerRepository.findByCategory(category);
    }

    public Provider getProviderById(@NonNull Long id) {
        return providerRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider not found"));
    }

    public void updateAvailability(@NonNull Long providerId, boolean isAvailable) {
        Provider provider = getProviderById(providerId);
        provider.setIsAvailable(isAvailable);
        providerRepository.save(provider);
    }

    public List<Provider> searchProviders(String keyword) {
        return providerRepository.searchProviders(keyword);
    }

    public List<Provider> filterProviders(ProviderCategory category, String city) {
        return providerRepository.findByFilters(category, city);
    }
}
