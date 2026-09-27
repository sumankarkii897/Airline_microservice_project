package com.himalayan.config;

import com.himalayan.entity.User;
import com.himalayan.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;
    @Value("${jwt.secret.key}")
    private String secretKey;

    public SecretKey getSecretKey(){
        return  Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("type", TokenType.ACCESS.name())
                .issuedAt( new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSecretKey())


                .compact();

    }
    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("type", TokenType.REFRESH.name())
                .issuedAt( new Date())
                .expiration( new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSecretKey())
                .compact();
    }

    public TokenType getTokenType(String token){
        String type = extractClaims(token,claims -> claims.get("type",String.class));
        return TokenType.valueOf(type);
    }
    public boolean isAccessToken(String token){
        return getTokenType(token)==TokenType.ACCESS;
    }

    public boolean isRefreshToken(String token){
        return getTokenType(token) == TokenType.REFRESH;
    }

    private<T> T extractClaims(String token, Function<Claims,T> claimsFunction){
        return claimsFunction.apply(
                Jwts.parser()
                        .verifyWith(getSecretKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }
    public String getUsernameFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
