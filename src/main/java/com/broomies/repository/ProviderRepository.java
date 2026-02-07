package com.broomies.repository;

import com.broomies.entity.Provider;
import com.broomies.enums.ProviderCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findByCategory(ProviderCategory category);

    @Query("SELECT p FROM Provider p WHERE p.user.name LIKE %?1% OR p.bio LIKE %?1% OR p.user.address LIKE %?1% OR p.skills LIKE %?1%")
    List<Provider> searchProviders(String keyword);

    @Query("SELECT p FROM Provider p WHERE (:category IS NULL OR p.category = :category) AND (:city IS NULL OR p.user.address LIKE %:city%)")
    List<Provider> findByFilters(@Param("category") ProviderCategory category, @Param("city") String city);
}
