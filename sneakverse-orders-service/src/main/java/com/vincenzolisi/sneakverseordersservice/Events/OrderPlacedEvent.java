package com.vincenzolisi.sneakverseordersservice.Events;

import java.time.LocalDateTime;
import java.util.List;

public class OrderPlacedEvent {

    private String eventId; 
    private Integer userId;
    private String userEmail;
    private String paymentMethod;
    private List<OrderPlacedItemEvent> items;
    private String timestamp;

    public OrderPlacedEvent() {}

    public OrderPlacedEvent(Integer userId, String paymentMethod, List<OrderPlacedItemEvent> items) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.items = items;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public List<OrderPlacedItemEvent> getItems() {
        return items;
    }

    public void setItems(List<OrderPlacedItemEvent> items) {
        this.items = items;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
