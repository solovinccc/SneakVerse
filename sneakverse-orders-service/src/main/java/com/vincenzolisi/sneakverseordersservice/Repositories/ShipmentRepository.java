package com.vincenzolisi.sneakverseordersservice.Repositories;

import com.vincenzolisi.sneakverseordersservice.Models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
}
