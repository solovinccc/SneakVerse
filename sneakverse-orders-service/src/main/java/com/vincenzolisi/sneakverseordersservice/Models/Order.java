package com.vincenzolisi.sneakverseordersservice.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "paymentMethod", nullable = false)
    private String paymentMethod;

    @Column(name = "deliveryDate")
    private LocalDateTime deliveryDate;

    @Column(name = "total")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus status = OrderStatus.PROCESSING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Shipment shipment;


    public Order() {  }

    public Order(int orderId, LocalDateTime orderDate, Integer userId, String userEmail, String paymentMethod, LocalDateTime deliveryDate, BigDecimal total, OrderStatus status, List<OrderItem> items, Shipment shipment) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.userEmail = userEmail;
        this.paymentMethod = paymentMethod;
        this.deliveryDate = deliveryDate;
        this.total = total;
        this.status = status;
        this.items = items;
        this.shipment = shipment;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus getStatus() { return status; }

    public void setStatus(com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus status) { this.status = status; }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", userId=" + userId +
                ", paymentMethod=" + paymentMethod +
                ", items=" + items +
                ", shipment=" + shipment +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }
}