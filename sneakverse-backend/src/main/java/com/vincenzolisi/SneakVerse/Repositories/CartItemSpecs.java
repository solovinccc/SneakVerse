package com.vincenzolisi.SneakVerse.Repositories;

import com.vincenzolisi.SneakVerse.Models.CartItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemSpecs {

    public static Specification<CartItem> byUserAndShoe(Integer userId, Integer shoeId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("userId"), userId),
                cb.equal(root.get("shoeId"), shoeId)
        );
    }
}
