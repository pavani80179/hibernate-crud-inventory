package com.pavani.hibernate_crud_inventory;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // You can test AUTO & SEQUENCE later
    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantity;

    public Product() {}

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setPrice(double price) { this.price = price; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
