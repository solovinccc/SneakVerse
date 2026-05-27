package com.vincenzolisi.sneakverseproducts.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "shoes")
public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoeId")
    private int shoeId;

    @Column(name = "shoeName", nullable = false)
    private String shoeName;

    @Column(name = "shoePrice", nullable = false)
    private BigDecimal shoePrice;

    @Column(name = "shoeSize", nullable = false)
    private Float shoeSize;

    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    public Shoe() {}

    public int getShoeId() { return shoeId; }
    public void setShoeId(int shoeId) { this.shoeId = shoeId; }
    public String getShoeName() { return shoeName; }
    public void setShoeName(String shoeName) { this.shoeName = shoeName; }
    public BigDecimal getShoePrice() { return shoePrice; }
    public void setShoePrice(BigDecimal shoePrice) { this.shoePrice = shoePrice; }
    public Float getShoeSize() { return shoeSize; }
    public void setShoeSize(Float shoeSize) { this.shoeSize = shoeSize; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }
}