package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.util.List;

public class CheckoutRequestDTO {

    private Integer userId;
    private String paymentMethod;
    private List<CheckoutItemDTO> items;
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CheckoutRequestDTO () {
    }

    public CheckoutRequestDTO(Integer userId, String paymentMethod, List<CheckoutItemDTO> items) {
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.items = items;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<CheckoutItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemDTO> items) {
        this.items = items;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "CheckoutRequestDTO{" +
                "userId=" + userId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", items=" + items +
                '}';
    }
}