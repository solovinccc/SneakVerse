package com.vincenzolisi.sneakverseproducts.ModelsDTO;
import java.math.BigDecimal;

public class ShoeDTO {
    private int shoeId;
    private String shoeName;
    private BigDecimal shoePrice;
    private Float shoeSize;
    private Integer brandId;
    private String imageUrl;

    public ShoeDTO() {}

    public ShoeDTO(int shoeId, String shoeName, BigDecimal shoePrice, Float shoeSize, Integer brandId, String imageUrl) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.shoePrice = shoePrice;
        this.shoeSize = shoeSize;
        this.brandId = brandId;
        this.imageUrl = imageUrl;
    }

    public int getShoeId() { return shoeId; }
    public void setShoeId(int shoeId) { this.shoeId = shoeId; }
    public String getShoeName() { return shoeName; }
    public void setShoeName(String shoeName) { this.shoeName = shoeName; }
    public BigDecimal getShoePrice() { return shoePrice; }
    public void setShoePrice(BigDecimal shoePrice) { this.shoePrice = shoePrice; }
    public Float getShoeSize() { return shoeSize; }
    public void setShoeSize(Float shoeSize) { this.shoeSize = shoeSize; }
    public Integer getBrandId() { return brandId; }
    public void setBrandId(Integer brandId) { this.brandId = brandId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}