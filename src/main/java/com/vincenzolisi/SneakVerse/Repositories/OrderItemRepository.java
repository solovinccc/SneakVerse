package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_OrderId(Integer orderId);

    List<OrderItem> findByOrder_OrderIdIn(List<Integer> orderIds);

    void deleteByOrder_OrderId(Integer orderId);

    void deleteByOrder_OrderIdIn(List<Integer> orderIds);

    List<OrderItem> findByShoe_ShoeId(Integer shoeId);
}
