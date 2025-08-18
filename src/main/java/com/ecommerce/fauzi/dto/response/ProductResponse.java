package com.ecommerce.fauzi.dto.response;

import java.util.UUID;

public record ProductResponse(UUID productId, String productName, int price, String desc) {
    
}
