package com.vincenzolisi.sneakverseordersservice.Repositories;

import com.vincenzolisi.sneakverseordersservice.Models.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Integer> {
}
