package com.himalayan.mapper;

import com.himalayan.entity.User;
import com.himalayan.payload.request.SignUpRequest;
import com.himalayan.payload.response.AuthResponse;
import com.himalayan.payload.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
   public UserResponse toUserResponse(User user){
       if(user == null){
           return null;
       }
       return UserResponse.builder()
               .id(user.getId())
               .email(user.getEmail())
               .fullName(user.getFullName())
               .phone(user.getPhone())
               .role(user.getRole())
               .build();
    }
 public  User toEntity (SignUpRequest request){
        if(request == null){
            return null;
        }
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .phone(request.getPhone())

                .build();
   }

  public AuthResponse toAuthResponse(String accessToken, String refreshToken){
        if(accessToken == null || refreshToken == null){
            return null;
        }
        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
   }
}
