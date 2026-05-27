package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.util.List;

public class BrandDTO {

    private Integer brandId;
    private String brandName;
    private List<Integer> shoeIds;

    public BrandDTO() {  }

    public BrandDTO(Integer brandId, String brandName, List<Integer> shoeIds) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.shoeIds = shoeIds;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<Integer> getShoeIds() {
        return shoeIds;
    }

    public void setShoeIds(List<Integer> shoeIds) {
        this.shoeIds = shoeIds;
    }

    @Override
    public String toString() {
        return "BrandDTO{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", shoeIds=" + shoeIds +
                '}';
    }
}
