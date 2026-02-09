package com.vincenzolisi.SneakVerse.ModelsDTO;

public class CheckoutItemDTO {

    private Integer shoeId;
    private Integer quantity;

    public CheckoutItemDTO() {

    }

    public CheckoutItemDTO(Integer shoeId, Integer quantity) {
        super();
        this.shoeId = shoeId;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getShoeId() {
        return shoeId;
    }

    public void setShoeId(Integer shoeId) {
        this.shoeId = shoeId;
    }

    @Override
    public String toString() {
        return "CheckoutItemDTO{" +
                "shoeId=" + shoeId +
                ", quantity=" + quantity +
                '}';
    }
}
