package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderViewDTO {
    private Integer orderId;
    private LocalDateTime orderDate;
    private Integer userId;
    private String paymentMethod;
    private BigDecimal total;

    private Integer shipmentId;
    private Integer courierId;
    private String courierPhoneNumber;

    private List<OrderItemViewDTO> items;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getCourierPhoneNumber() {
        return courierPhoneNumber;
    }

    public void setCourierPhoneNumber(String courierPhoneNumber) {
        this.courierPhoneNumber = courierPhoneNumber;
    }

    public List<OrderItemViewDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemViewDTO> items) {
        this.items = items;
    }
}
