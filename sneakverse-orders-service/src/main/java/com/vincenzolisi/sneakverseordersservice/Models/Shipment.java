package com.vincenzolisi.sneakverseordersservice.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipmentId")
    private Integer shipmentId;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false, unique = true)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "courierId", nullable = false)
    @JsonIgnore
    private Courier courier;

    public Shipment() {  }

    public Shipment(Order order, Courier courier) {
        this.order = order;
        this.courier = courier;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId=" + shipmentId +
                ", order=" + (order != null ? order.getOrderId() : "null")  +
                ", courier=" + (courier != null ? courier.getCourierId() : "null") +
                '}';
    }
}
