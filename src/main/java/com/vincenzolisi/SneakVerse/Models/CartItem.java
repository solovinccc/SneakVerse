package com.vincenzolisi.SneakVerse.Models;


import jakarta.persistence.*;

@Entity
@Table(
        name = "cart_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "shoeId"})
)

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "shoeId", nullable = false)
    private Integer shoeId;

    @Column(nullable = false)
    private int quantity;

    public CartItem() {
    }

    public CartItem(Integer cartItemId, Integer userId, Integer shoeId, int quantity) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.shoeId = shoeId;
        this.quantity = quantity;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShoeId() {
        return shoeId;
    }

    public void setShoeId(Integer shoeId) {
        this.shoeId = shoeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
