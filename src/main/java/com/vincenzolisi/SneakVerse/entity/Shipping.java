package com.vincenzolisi.SneakVerse.entity;

import jakarta.persistence.*;

@Entity
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Courier courier;
}
