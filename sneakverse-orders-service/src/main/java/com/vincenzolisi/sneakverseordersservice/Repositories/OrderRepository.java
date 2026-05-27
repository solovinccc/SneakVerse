package com.vincenzolisi.sneakverseordersservice.Repositories;

import com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus;
import com.vincenzolisi.sneakverseordersservice.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    List<Order> findByUserIdAndStatusNotOrderByOrderDateDesc(Integer userId, com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus status);
    List<Order> findByStatusAndDeliveryDateBefore(OrderStatus status, LocalDateTime now);
}
