package com.ecommerce.fauzi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {
   private String productName;
   private int price; 
   private String desc;
}
