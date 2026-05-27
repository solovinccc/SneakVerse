package com.vincenzolisi.sneakverseproducts.Repositories;

import com.vincenzolisi.sneakverseproducts.Models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findByBrandNameIgnoreCase(String brandName);
}
