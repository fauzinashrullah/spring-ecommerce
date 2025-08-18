package com.ecommerce.fauzi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.fauzi.model.Product;

public interface JpaProductRepository extends JpaRepository<Product, UUID>{
    
}
