package com.vincenzolisi.SneakVerse.ModelsDTO;

public class OrderItemDTO {

    private Integer orderItemId;
    private Integer quantity;
    private Integer orderId;
    private Integer shoeId;

    public OrderItemDTO() {  }

    public OrderItemDTO(Integer orderItemId, Integer quantity, Integer orderId, Integer shoeId) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.shoeId = shoeId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getShoeId() {
        return shoeId;
    }

    public void setShoeId(Integer shoeId) {
        this.shoeId = shoeId;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "orderItemId=" + orderItemId +
                ", quantity=" + quantity +
                ", orderId=" + orderId +
                ", shoeId=" + shoeId +
                '}';
    }
}
