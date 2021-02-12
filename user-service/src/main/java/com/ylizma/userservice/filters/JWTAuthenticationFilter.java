package com.ylizma.userservice.filters;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ylizma.userservice.entities.UserApp;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final String SECRET_KEY = "2a7UQi3e9ZsmTExTxvynAPa69EXgvKjTzH2ZOolLHO2vPjBZbBiUHpgXJzyHFvp";
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserApp user = new UserApp();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        String accessToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 1000))
                .setIssuer(request.getRequestURI())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 1000))
                .setIssuer(request.getRequestURI())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        Map<String, String> tokens = new HashMap<>();
        tokens.put("Access_Token", accessToken);
        tokens.put("Refresh_Token", refreshToken);
        response.setContentType("application/json");
        new JsonMapper().writeValue(response.getOutputStream(), accessToken);
    }
}
