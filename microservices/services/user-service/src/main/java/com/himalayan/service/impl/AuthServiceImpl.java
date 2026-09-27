package com.himalayan.service.impl;
import com.himalayan.config.AuthUser;
import com.himalayan.config.JwtUtil;
import com.himalayan.entity.RefreshToken;
import com.himalayan.entity.User;
import com.himalayan.enums.UserRole;
import com.himalayan.exception.AlreadyExistsException;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.AuthMapper;
import com.himalayan.payload.request.LoginRequest;
import com.himalayan.payload.request.SignUpRequest;
import com.himalayan.payload.response.AuthResponse;
import com.himalayan.payload.response.UserResponse;
import com.himalayan.repository.RefreshTokenRepository;
import com.himalayan.repository.UserRepository;
import com.himalayan.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;
   @Override
    public AuthResponse Login(LoginRequest request) {
        Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
       AuthUser authUser = (AuthUser) authenticate.getPrincipal();
      if(authUser==null){
          throw  new BadCredentialsException("Bad credentials");
      }
       User user = authUser.getUser();
      user.setLastLogin(LocalDateTime.now());
      userRepository.save(user);
      String token = jwtUtil.generateToken(user);
      String refreshToken = jwtUtil.generateRefreshToken(user);


       RefreshToken savedRefreshToken = RefreshToken.builder()
               .token(refreshToken)
               .user(user)
               .revoked(false)
               .expiresAt(new Date(System.currentTimeMillis()+ refreshExpiration))
               .build();

       refreshTokenRepository.save(savedRefreshToken);
        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserResponse singUp(SignUpRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AlreadyExistsException("User with this email already exists");
        }

        UserRole userRole = UserRole.USER;
        User user = User.builder()
                .email(request.getEmail())
                .role(userRole)
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .fullName(request.getFullName())
                .build();

User savedUser =userRepository.save(user);
        return authMapper.toUserResponse(savedUser);
    }

    @Override
    public AuthResponse refreshToken(String token) {
        RefreshToken savedToken = refreshTokenRepository.findByToken(token).orElseThrow(()-> new ResourceNotFoundException("Refresh token not found"));
        if (savedToken.isRevoked()) {
            throw new BadCredentialsException("Refresh token has been revoked");
        }
        if(!jwtUtil.isRefreshToken(token)){
            throw new BadCredentialsException("Invalid refresh token");
        }

        String username = jwtUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username).orElseThrow(
                ()-> new ResourceNotFoundException("User with this email already exists")
        );

        AuthUser authUser = AuthUser.builder()
                .user(user).build();
        if(!jwtUtil.isTokenValid(token,authUser)){
            throw new BadCredentialsException("Invalid refresh token");
        }
        String accessToken = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(token)
                .build();
    }

    public UserResponse updateUserRole(Long id,UserRole userRole) {
       User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesn't exit"));
     user.setRole(userRole);
     return authMapper.toUserResponse(userRepository.save(user));
    }
}
