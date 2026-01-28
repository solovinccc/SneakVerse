package com.vincenzolisi.SneakVerse.ModelsDTO;

public class CourierDTO {

    private int courierId;
    private int phoneNumber;
    private int shipmentId;

    public CourierDTO() {  }

    public CourierDTO(int courierId, int phoneNumber, int shipmentId) {
        this.courierId = courierId;
        this.phoneNumber = phoneNumber;
        this.shipmentId = shipmentId;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public String toString() {
        return "CourierDTO{" +
                "courierId=" + courierId +
                ", phoneNumber=" + phoneNumber +
                ", shipmentId=" + shipmentId +
                '}';
    }
}
