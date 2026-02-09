package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("""
        SELECT b
        FROM Brand b
        WHERE LOWER(b.brandName) = LOWER(:brandName)
    """)
    Optional<Brand> findByNameIgnoreCase(@Param("brandName") String brandName);
}
