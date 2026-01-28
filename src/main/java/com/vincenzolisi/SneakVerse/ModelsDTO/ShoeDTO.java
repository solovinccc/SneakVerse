package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.math.BigDecimal;
import java.util.List;

public class ShoeDTO {

    private int shoeId;
    private String shoeName;
    private BigDecimal shoePrice;
    private Integer shoeSize;
    private List<Integer> items;
    private Integer brandId;

    public ShoeDTO() {  }

    public ShoeDTO(int shoeId, String shoeName, BigDecimal shoePrice, Integer shoeSize, List<Integer> items, Integer brandId) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.shoePrice = shoePrice;
        this.shoeSize = shoeSize;
        this.items = items;
        this.brandId = brandId;
    }

    public int getShoeId() {
        return shoeId;
    }

    public void setShoeId(int shoeId) {
        this.shoeId = shoeId;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public BigDecimal getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(BigDecimal shoePrice) {
        this.shoePrice = shoePrice;
    }

    public Integer getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(Integer shoeSize) {
        this.shoeSize = shoeSize;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "ShoeDTO{" +
                "shoeId=" + shoeId +
                ", shoeName='" + shoeName + '\'' +
                ", shoePrice=" + shoePrice +
                ", shoeSize=" + shoeSize +
                ", items=" + items +
                ", brandId=" + brandId +
                '}';
    }

}
