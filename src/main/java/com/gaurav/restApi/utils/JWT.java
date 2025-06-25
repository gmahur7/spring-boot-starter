package com.gaurav.restApi.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWT {

    public String generateToken (String username){
        String secret = "ARCEUS";
        return Jwts.builder()
        .claim("sub", username)
        .claim("iat", System.currentTimeMillis() / 1000)
        .claim("exp", (System.currentTimeMillis() + 86400000) / 1000)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
        .compact();
    }
    
}
