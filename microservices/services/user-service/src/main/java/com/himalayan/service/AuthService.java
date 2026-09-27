package com.himalayan.service;


import com.himalayan.payload.request.LoginRequest;
import com.himalayan.payload.request.SignUpRequest;
import com.himalayan.payload.response.AuthResponse;
import com.himalayan.payload.response.UserResponse;

public interface AuthService {

    AuthResponse Login(LoginRequest request);

   UserResponse singUp(SignUpRequest request);

   AuthResponse refreshToken(String token);


}
