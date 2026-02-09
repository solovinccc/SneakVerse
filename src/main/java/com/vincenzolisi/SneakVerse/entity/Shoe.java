package com.vincenzolisi.SneakVerse.entity;

import jakarta.persistence.*;

@Entity
public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private Float price;
    private boolean isSold;
    private int size;

    @ManyToOne
    private Brand brand;
}
