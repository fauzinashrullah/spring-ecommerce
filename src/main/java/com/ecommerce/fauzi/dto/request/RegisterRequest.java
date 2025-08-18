package com.ecommerce.fauzi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
