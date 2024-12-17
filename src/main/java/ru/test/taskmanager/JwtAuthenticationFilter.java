package ru.test.taskmanager;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.test.taskmanager.models.entities.JwtDetails;
import ru.test.taskmanager.repositories.JwtRepository;
import ru.test.taskmanager.services.AuthenticationService;
import ru.test.taskmanager.utils.JwtUtils;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtRepository tokens;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException 
    {
        String jwtToken = this.jwtUtils.resolveToken(request);
        if (jwtToken != null)
        {
            String username = this.extractUsernameFromToken(jwtToken);
            SecurityContext context = SecurityContextHolder.getContext();

            if (username != null && context.getAuthentication() == null) 
            {
                UsernamePasswordAuthenticationToken authToken = this.authService.createAuthenticationToken(username, request);

                if (this.validateJwtToken(jwtToken, username)) 
                {
                    context.setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractUsernameFromToken(String token)
    {
        try
        {
            return this.jwtUtils.extractUsername(token);
        }
        catch (ExpiredJwtException exception)
        {
            return null;
        }
    }

    private boolean validateJwtToken(String token, String username)
    {
        return this.jwtUtils.validateToken(token, username)
            && this.tokens.findByToken(token).map(JwtDetails::isActive).orElse(false);
    }
}
