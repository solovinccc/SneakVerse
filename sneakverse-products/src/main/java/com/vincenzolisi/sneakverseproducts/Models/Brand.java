package com.vincenzolisi.sneakverseproducts.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brandId")
    private int brandId;

    @Column(name = "brandName", nullable = false)
    private String brandName;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shoe> shoes;

    public Brand() {}

    public int getBrandId() { return brandId; }
    public void setBrandId(int brandId) { this.brandId = brandId; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public List<Shoe> getShoes() { return shoes; }
    public void setShoes(List<Shoe> shoes) { this.shoes = shoes; }
}