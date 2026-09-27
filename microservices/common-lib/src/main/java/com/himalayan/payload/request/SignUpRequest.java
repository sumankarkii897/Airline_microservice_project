package com.himalayan.payload.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @NotBlank(message = "Name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10,
            message = "Phone number length must be 10")
    private String phone;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 50,
            message = "Password length must be between 8 and 50 characters.")
    private String password;

}
