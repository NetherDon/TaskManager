package ru.test.taskmanager.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils 
{
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private static final long EXPIRATION_TIME = 60 * 60 * 1000;

    private static final String SECRET_KEY_STRING = "738c9e8d140c0258f9e1ccb93c089e64827631b40d7a55a4bce0fb5878788a08";
    private static final Key SECRET_KEY = new SecretKeySpec(
        Base64.getDecoder().decode(SECRET_KEY_STRING), 
        SignatureAlgorithm.HS256.getJcaName()
    );

    public String generateTokenFor(String username) 
    {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact();
    }

    public String extractUsername(String token) throws ExpiredJwtException
    {
        return this.extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) throws ExpiredJwtException
    {
        return this.extractUsername(token).equals(username)
            && !this.isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) 
    {
        return this.extractClaims(token)
            .getExpiration()
            .before(new Date());
    }

    public String resolveToken(String header)
    {
        if (StringUtils.hasText(header) && header.startsWith(AUTHORIZATION_HEADER_PREFIX)) 
        {
            return header.substring(AUTHORIZATION_HEADER_PREFIX.length());
        }
        return null;
    }

    public String resolveToken(HttpServletRequest request) 
    {
        return this.resolveToken(request.getHeader(AUTHORIZATION_HEADER_NAME));
    }

    private Claims extractClaims(String token) 
    {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
