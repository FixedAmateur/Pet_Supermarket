package com.laborexport.pet_supermarket.service;

import com.laborexport.pet_supermarket.model.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails, Long time);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long time);

    long getExpirationTime();

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}
