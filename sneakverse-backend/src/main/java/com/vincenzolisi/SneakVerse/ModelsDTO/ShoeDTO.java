package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.math.BigDecimal;
import java.util.List;

public class ShoeDTO {

    private Integer shoeId;
    private String shoeName;
    private BigDecimal shoePrice;
    private Float shoeSize;
    private List<Integer> items;
    private Integer brandId;
    private String imageUrl;

    public ShoeDTO() { }

    public ShoeDTO(Integer shoeId, String shoeName, BigDecimal shoePrice, Float shoeSize, List<Integer> items, Integer brandId, String imageUrl) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.shoePrice = shoePrice;
        this.shoeSize = shoeSize;
        this.items = items;
        this.brandId = brandId;
        this.imageUrl = imageUrl;
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

    public BigDecimal getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(BigDecimal shoePrice) {
        this.shoePrice = shoePrice;
    }

    public Float getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(Float shoeSize) {
        this.shoeSize = shoeSize;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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