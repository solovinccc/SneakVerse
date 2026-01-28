package com.vincenzolisi.SneakVerse.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

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
    private int shoeSize;

    @OneToMany(mappedBy = "shoe", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    public Shoe() {  }

    public Shoe(String shoeName, BigDecimal shoePrice, int shoeSize, Brand brand) {
        this.shoeName = shoeName;
        this.shoePrice = shoePrice;
        this.shoeSize = shoeSize;
        this.brand = brand;
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

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        this.shoeSize = shoeSize;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "shoeId=" + shoeId +
                ", shoeName='" + shoeName + '\'' +
                ", shoePrice=" + shoePrice +
                ", shoeSize=" + shoeSize +
                ", brand=" + (brand != null ? brand.getBrandId() : "null") +
                '}';
    }

}
