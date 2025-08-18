package com.ecommerce.fauzi.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    private UUID id;
    private String productName;
    private int price;
    private String description;
    private boolean isDeleted = false;
}
