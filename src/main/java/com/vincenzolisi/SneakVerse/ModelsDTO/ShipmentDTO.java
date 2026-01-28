package com.vincenzolisi.SneakVerse.ModelsDTO;


public class ShipmentDTO {

    private Integer shipmentId;
    private Integer orderId;
    private Integer courierId;

    public ShipmentDTO() {  }

    public ShipmentDTO(Integer shipmentId, Integer orderId, Integer courierId) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.courierId = courierId;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    @Override
    public String toString() {
        return "ShipmentDTO{" +
                "shipmentId=" + shipmentId +
                ", orderId=" + orderId +
                ", courierId=" + courierId +
                '}';
    }
}
