package com.fotaleza.fortalezaapi.security.jwt;

import com.fotaleza.fortalezaapi.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtils {

    @Value("${fortaleza.app.jwtSecret}")
    private String jwtSecret;

    @Value("${fortaleza.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private static final long JWT_TIME_REFRESH_VALIDATE = 1000 * 60 * 60 * 24;

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String auhtToken, UserDetailsImpl userDetails) {

        try {
            return extractClaim(auhtToken, Claims::getSubject).equals(userDetails.getUsername())
                    && !extractClaim(auhtToken, Claims::getExpiration).before(new Date());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;

    }

    public  String generateToken(UserDetailsImpl userDetails, List<String> roles) {
         Map<String, Object> claims = new HashMap<>();
         claims.put("roles", roles);
        claims.put("firstName", userDetails.getFirstName());
        claims.put("lastName", userDetails.getLastName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public  String generateRefreshToken(UserDetailsImpl userDetails, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("firstName", userDetails.getFirstName());
        claims.put("lastName", userDetails.getLastName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TIME_REFRESH_VALIDATE))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNamefromJwtToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

}
