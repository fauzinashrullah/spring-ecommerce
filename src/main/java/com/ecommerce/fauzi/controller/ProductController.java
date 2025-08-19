package com.ecommerce.fauzi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.fauzi.dto.request.ProductRequest;
import com.ecommerce.fauzi.dto.response.ApiResponse;
import com.ecommerce.fauzi.dto.response.ProductResponse;
import com.ecommerce.fauzi.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService service;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createProduct(@RequestBody ProductRequest request) {
        service.createProduct(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Add product success", Map.of()));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {
        List<ProductResponse> responses = service.getAllProducts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get products success", responses));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getDetailProduct(@PathVariable String productId) {
        ProductResponse response = service.getDetailProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get detail product success", response));
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable String productId, @RequestBody ProductRequest request) {
        service.updateProduct(productId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Update product success", Map.of()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>>  deleteProduct(@PathVariable String productId){
        service.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delete product success", Map.of()));
    }

    @PostMapping("/checkout/{productId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createTransaction(@PathVariable String productId) {
        Map<String, Object> response = service.checkoutProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Checkout success", response));
    }
    
}
