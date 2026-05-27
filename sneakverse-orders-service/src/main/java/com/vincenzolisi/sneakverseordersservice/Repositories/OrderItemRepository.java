package com.vincenzolisi.sneakverseordersservice.Repositories;

import com.vincenzolisi.sneakverseordersservice.Models.OrderItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
