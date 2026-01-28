package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
