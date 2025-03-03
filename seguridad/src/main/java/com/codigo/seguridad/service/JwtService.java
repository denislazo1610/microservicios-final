package com.codigo.seguridad.service;

import com.codigo.seguridad.aggregates.request.SignInRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String validandoToken(UserDetails userDetails, String token);
    boolean validateToken(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isRefreshToken(String token);
}
