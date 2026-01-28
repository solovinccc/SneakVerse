package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private int orderId;
    private LocalDateTime orderDate;
    private int userId;
    private List<Integer> orderItemId;
    private Integer shipmentId;

    public OrderDTO () {  }

    public OrderDTO(int orderId, LocalDateTime orderDate, int userId, List<Integer> orderItemId, Integer shipmentId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.orderItemId = orderItemId;
        this.shipmentId = shipmentId;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(List<Integer> orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", userId=" + userId +
                ", orderItemId=" + orderItemId +
                ", shipmentId=" + shipmentId +
                '}';
    }
}
