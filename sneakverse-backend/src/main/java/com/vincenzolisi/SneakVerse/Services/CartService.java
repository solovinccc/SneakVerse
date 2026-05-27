package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.CartItem;
import com.vincenzolisi.SneakVerse.Repositories.CartItemRepository;
import com.vincenzolisi.SneakVerse.Repositories.CartItemSpecs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public CartItem addToCart(Integer userId, Integer shoeId) {
        Specification<CartItem> spec =
                CartItemSpecs.byUserAndShoe(userId, shoeId);

        return cartItemRepository.findOne(spec)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + 1);
                    return item;
                })
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setUserId(userId);
                    item.setShoeId(shoeId);
                    item.setQuantity(1);
                    return cartItemRepository.save(item);
                });
    }
}
