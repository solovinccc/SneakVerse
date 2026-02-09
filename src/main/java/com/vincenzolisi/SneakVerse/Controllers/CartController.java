package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.Models.CartItem;
import com.vincenzolisi.SneakVerse.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartItem addToCart(@RequestParam Integer userId, @RequestParam Integer shoeId) {
        return cartService.addToCart(userId, shoeId);
    }
}
