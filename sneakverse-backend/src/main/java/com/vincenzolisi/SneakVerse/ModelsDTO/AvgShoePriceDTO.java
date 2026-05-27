package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.math.BigDecimal;

public class AvgShoePriceDTO {

    private BigDecimal averagePrice;

    public AvgShoePriceDTO(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }
}
