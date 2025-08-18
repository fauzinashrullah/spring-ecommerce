package com.ecommerce.fauzi.dto.request;

import com.ecommerce.fauzi.dto.response.ProductResponse;
import com.ecommerce.fauzi.dto.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MidtransRequest {
    private ProductResponse product;
    private UserResponse user;
}
