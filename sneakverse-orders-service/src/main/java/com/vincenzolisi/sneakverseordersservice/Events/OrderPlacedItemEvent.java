package com.vincenzolisi.sneakverseordersservice.Events;

public class OrderPlacedItemEvent {

    private Integer shoeId;
    private Integer quantity;

    public OrderPlacedItemEvent() {   }

    public OrderPlacedItemEvent(Integer shoeId, Integer quantity) {
        this.shoeId = shoeId;
        this.quantity = quantity;
    }

    public Integer getShoeId() {
        return shoeId;
    }

    public void setShoeId(Integer shoeId) {
        this.shoeId = shoeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
