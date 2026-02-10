package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.Order;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_UserIdAndOrderDateAfterOrderByOrderDateDesc(Integer userId, LocalDateTime after);
    void deleteByOrderDateBefore(LocalDateTime before);
}

