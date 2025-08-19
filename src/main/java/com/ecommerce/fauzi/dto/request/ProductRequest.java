package com.ecommerce.fauzi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {
   @NotBlank(message = "Product name is required")
   private String productName;

   @Min(value = 100, message = "Price must be at least 100")
   private int price; 

   private String desc;
}
