package com.suchit.jwt.service;

import com.suchit.jwt.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

//    @Value("${jwt.secret}")
//    private String secretkey;

    private String secretkey = "";

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        System.out.println("================================================================");
        System.out.println("This is a generateToken method of JwtService class.");

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hours
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        System.out.println("================================================================");
        System.out.println("This is a getKey method of JwtService class.");
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);

//        bellow code is from ChatGPT if we don't want to generate the secret key.
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String extractUserName(String token) {
        System.out.println("================================================================");
        System.out.println("This is a extractUserName method of JwtService class.");
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        System.out.println("================================================================");
        System.out.println("This is a extractClaim method of JwtService class.");
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        System.out.println("================================================================");
        System.out.println("This is a extractAllClaims method of JwtService class.");
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("================================================================");
        System.out.println("This is a validateToken method of JwtService class.");
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        System.out.println("================================================================");
        System.out.println("This is a isTokenExpired method of JwtService class.");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        System.out.println("================================================================");
        System.out.println("This is a extractExpiration method of JwtService class.");
        return extractClaim(token, Claims::getExpiration);
    }
}
