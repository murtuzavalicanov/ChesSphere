package com.chessphere.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

//    public String generateToken(String email, String role) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", role); // Gateway bu rola baxıb icazə verib-verməyəcəyinə qərar verə bilər
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(email)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 saatlıq
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userDetails.getUsername());

        // Obyektləri (GrantedAuthority) sadə mətnlərə (String) çeviririk
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // "ROLE_USER" və ya "ADMIN" stringini götürür
                .collect(Collectors.toList());

        claims.put("roles", roles); // İndi JSON-da sadə ["ROLE_USER", "ADMIN"] siyahısı olacaq

        return createToken(claims, userDetails.getUsername());
    }

    // Bu metod, verilen teleblere uyqun olaraq bir token oluşturur
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token geçerliliği 10 saat
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();
    }
}