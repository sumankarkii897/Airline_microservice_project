package com.himalayan.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid Email format.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 50,
            message = "Password length must be between 8 and 50 characters.")
    private String password;
}