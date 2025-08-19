package com.ecommerce.fauzi.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.fauzi.dto.request.MidtransRequest;
import com.ecommerce.fauzi.dto.request.ProductRequest;
import com.ecommerce.fauzi.dto.response.ProductResponse;
import com.ecommerce.fauzi.dto.response.UserResponse;
import com.ecommerce.fauzi.exception.NotFoundException;
import com.ecommerce.fauzi.model.Product;
import com.ecommerce.fauzi.model.User;
import com.ecommerce.fauzi.repository.JpaAuthRepository;
import com.ecommerce.fauzi.repository.JpaProductRepository;
import com.ecommerce.fauzi.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final JpaProductRepository repository;
    private final JpaAuthRepository userRepository;
    private final MidtransService midtransService;

    public void createProduct(ProductRequest request){
        UUID userId = getIdFromContextHolder();

        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        product.setSellerId(userId);
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
        Product product = findProduct(productId);
        return new ProductResponse(product.getId(), product.getProductName(), product.getPrice(), product.getDescription());
    }

    public void updateProduct(String productId, ProductRequest request){
        UUID userId = getIdFromContextHolder();

        Product product = findProduct(productId);

        if (!product.getSellerId().equals(userId)) {
            throw new NotFoundException("Product not found");
        }

        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDesc());
        repository.save(product);
    }

    public void deleteProduct(String productId){
        UUID userId = getIdFromContextHolder();

        Product product = findProduct(productId);

        if (!product.getSellerId().equals(userId)) {
            throw new NotFoundException("Product not found");
        }

        product.setDeleted(true);
        repository.save(product);
    }

    public Map<String, Object> checkoutProduct (String productId){
        UUID userId = getIdFromContextHolder();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));

        Product product = findProduct(productId);

        if (!product.getSellerId().equals(userId)) {
            throw new NotFoundException("Product not found");
        }

        ProductResponse productResponse = new ProductResponse(product.getId(), product.getProductName(), product.getPrice(), product.getDescription());
        UserResponse userResponse = new UserResponse(user.getName(), user.getEmail());
        
        return midtransService.createTransaction(new MidtransRequest(productResponse, userResponse));
    }

    private Product findProduct(String productId){
        Product product = repository.findById(UUID.fromString(productId))
            .orElseThrow(() -> new NotFoundException("Product not found"));

        if (product.isDeleted()) {
            throw new NotFoundException("Product not found");
        }
        return product;
    }

    private UUID getIdFromContextHolder(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }
}
