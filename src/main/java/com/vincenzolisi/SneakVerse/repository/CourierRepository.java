package com.vincenzolisi.SneakVerse.repository;

import com.vincenzolisi.SneakVerse.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Integer> {
}
