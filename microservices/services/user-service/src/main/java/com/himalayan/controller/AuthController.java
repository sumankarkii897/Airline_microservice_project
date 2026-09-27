package com.himalayan.controller;

import com.himalayan.payload.request.LoginRequest;
import com.himalayan.payload.request.SignUpRequest;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.AuthResponse;
import com.himalayan.payload.response.UserResponse;
import com.himalayan.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
@PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(
            ApiResponse.<AuthResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Login successful.")
                    .data(authService.Login(request))
                    .timestamp(new Date())
                    .build()
    );
}

@PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>>  signup(@Valid @RequestBody SignUpRequest request) {
//    System.out.println("into singup");
    return ResponseEntity.status(HttpStatus.CREATED).body(
           ApiResponse.<UserResponse>builder()
                   .statusCode(HttpStatus.CREATED.value())
                   .message("Sign up Successful.")
                   .data(authService.singUp(request))
                   .timestamp(new Date())
                   .build()
    );
}

@PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(String refreshToken){
    return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<AuthResponse>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Refresh token Successful.")
                    .data(authService.refreshToken(refreshToken))
                    .timestamp(new Date())
                    .build()
    );
}
}
