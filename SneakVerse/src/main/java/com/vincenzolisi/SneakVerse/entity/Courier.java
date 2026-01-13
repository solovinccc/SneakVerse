package com.vincenzolisi.SneakVerse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Courier {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private int phoneNumber;
}
