package com.vincenzolisi.SneakVerse.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "courier")
public class Courier {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "courierId")
    private int courierId;

    @Column(name = "phoneNumber")
    private int phoneNumber;

    @OneToOne(mappedBy = "courier", cascade = CascadeType.ALL)
    private Shipment shipment;

    public Courier() {  }

    public Courier(int phoneNumber, Shipment shipment) {
        this.phoneNumber = phoneNumber;
        this.shipment = shipment;
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

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "courierId=" + courierId +
                ", phoneNumber=" + phoneNumber +
                ", shipment=" + (shipment != null ? shipment.getShipmentId() : "null") +
                '}';
    }
}
