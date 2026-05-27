package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.math.BigDecimal;

public class OrderItemViewDTO {

    private Integer shoeId;
    private String shoeName;
    private Float shoeSize;
    private BigDecimal shoePrice;
    private Integer quantity;

    public OrderItemViewDTO() {

    }

    public OrderItemViewDTO(Integer shoeId, String shoeName, Float shoeSize, BigDecimal shoePrice, Integer quantity) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.shoeSize = shoeSize;
        this.shoePrice = shoePrice;
        this.quantity = quantity;
    }

    public Integer getShoeId() {
        return shoeId;
    }

    public void setShoeId(Integer shoeId) {
        this.shoeId = shoeId;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public Float getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(Float shoeSize) {
        this.shoeSize = shoeSize;
    }

    public BigDecimal getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(BigDecimal shoePrice) {
        this.shoePrice = shoePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemViewDTO{" +
                "shoeId=" + shoeId +
                ", shoeName='" + shoeName + '\'' +
                ", shoeSize=" + shoeSize +
                ", shoePrice=" + shoePrice +
                ", quantity=" + quantity +
                '}';
    }
}
