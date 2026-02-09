package com.vincenzolisi.SneakVerse.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courier")
public class Courier {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "courierId")
    private int courierId;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
    private List<Shipment> shipments = new ArrayList<>();

    public Courier() {  }

    public Courier(String phoneNumber, List<Shipment> shipments) {
        this.phoneNumber = phoneNumber;
        this.shipments = shipments;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "courierId=" + courierId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", shipments=" + shipments +
                '}';
    }
}
