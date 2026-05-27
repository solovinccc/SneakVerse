package com.vincenzolisi.SneakVerse.Repositories;


import com.vincenzolisi.SneakVerse.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>, JpaSpecificationExecutor<CartItem> {
}
