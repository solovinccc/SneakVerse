package com.vincenzolisi.sneakverseordersservice.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;

    @Column(name = "shoeId", nullable = false)
    private Integer shoeId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "shoePrice")
    private BigDecimal shoePrice;

    @Column(name = "shoeName")
    private String shoeName;

    public OrderItem() {  }

    public OrderItem(int orderItemId, Order order, Integer shoeId, int quantity, BigDecimal shoePrice, String shoeName) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.shoeId = shoeId;
        this.quantity = quantity;
        this.shoePrice = shoePrice;
        this.shoeName = shoeName;
    }

    public BigDecimal getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(BigDecimal shoePrice) {
        this.shoePrice = shoePrice;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + (order != null ? order.getOrderId() : "null") +
                ", shoe=" + shoeId +
                ", quantity=" + quantity +
                '}';
    }
}
