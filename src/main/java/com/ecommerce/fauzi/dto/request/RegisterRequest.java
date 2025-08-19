package com.ecommerce.fauzi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password minimal 8 character")
    private String password;
}
