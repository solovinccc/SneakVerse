package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoeRepository extends JpaRepository <Shoe, Integer>{

    @Query("""
        SELECT s
        FROM Shoe s
        WHERE s.brand.brandId = :brandId
        ORDER BY s.shoePrice ASC
    """)
    List<Shoe> findByBrandId(@Param("brandId") Integer brandId);
    List<Shoe> findByShoeIdIn(List<Integer> shoeIds);

}
