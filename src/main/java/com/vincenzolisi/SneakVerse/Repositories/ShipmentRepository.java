package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    List<Shipment> findByOrder_OrderIdIn(List<Integer> orderIds);
    List<Shipment> findByCourier_CourierId(Integer courierId);
    void deleteByOrder_OrderIdIn(List<Integer> orderIds);
}
