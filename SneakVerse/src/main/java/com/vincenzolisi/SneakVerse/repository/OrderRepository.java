package com.vincenzolisi.SneakVerse.repository;

import com.vincenzolisi.SneakVerse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
