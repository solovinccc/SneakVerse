package com.vincenzolisi.SneakVerse.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float price;
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Shoe shoe;
}
