package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.util.List;

public class CourierDTO {

    private Integer courierId;
    private String phoneNumber;
    private List<Integer> shipmentIds;

    public CourierDTO() {  }

    public CourierDTO(Integer courierId, String phoneNumber, List<Integer> shipmentIds) {
        this.courierId = courierId;
        this.phoneNumber = phoneNumber;
        this.shipmentIds = shipmentIds;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Integer> getShipmentIds() {
        return shipmentIds;
    }

    public void setShipmentIds(List<Integer> shipmentIds) {
        this.shipmentIds = shipmentIds;
    }

    @Override
    public String toString() {
        return "CourierDTO{" +
                "courierId=" + courierId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", shipmentIds=" + shipmentIds +
                '}';
    }
}
