package com.vincenzolisi.SneakVerse.repository;

import com.vincenzolisi.SneakVerse.entity.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeRepository extends JpaRepository <Shoe, Integer>{
}
