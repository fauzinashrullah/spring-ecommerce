package com.ecommerce.fauzi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.fauzi.dto.request.ProductRequest;
import com.ecommerce.fauzi.dto.response.ProductResponse;
import com.ecommerce.fauzi.model.Product;
import com.ecommerce.fauzi.repository.JpaProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final JpaProductRepository repository;

    public void createProduct(ProductRequest request){
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDesc());
        repository.save(product);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = repository.findAll();
        return products.stream()
            .filter(product -> !product.isDeleted())
            .map(product -> new ProductResponse(
                    product.getId(),
                    product.getProductName(), 
                    product.getPrice(), 
                    product.getDescription()
                    )
                )
            .toList();
    }

    public ProductResponse getDetailProduct(String productId){
        Product product = repository.findById(UUID.fromString(productId))
            .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.isDeleted()) {
            throw new RuntimeException("Product not found");
        }
        return new ProductResponse(product.getId(), product.getProductName(), product.getPrice(), product.getDescription());
    }

    public void updateProduct(String productId, ProductRequest request){
        Product product = repository.findById(UUID.fromString(productId))
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.isDeleted()) {
            throw new RuntimeException("Product not found");
        }

        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDesc());
        repository.save(product);
    }

    public void deleteProduct(String productId){
        Product product = repository.findById(UUID.fromString(productId))
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.isDeleted()) {
            throw new RuntimeException("Product not found");
        }
        product.setDeleted(true);
        repository.save(product);
    }

    public void checkoutProduct (String productId){
        Product product = repository.findById(UUID.fromString(productId))
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.isDeleted()) {
            throw new RuntimeException("Product not found");
        }
        // not yet
    }
}
