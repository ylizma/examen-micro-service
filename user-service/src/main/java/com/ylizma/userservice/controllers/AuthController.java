package com.ylizma.userservice.controllers;


import com.ylizma.userservice.entities.UserApp;
import com.ylizma.userservice.helper.LoginForm;
import com.ylizma.userservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginForm userDetails) throws Exception {
        System.out.println("login");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword())
            );
        } catch (Exception e) {
            throw new Exception(e);
        }
        final org.springframework.security.core.userdetails.UserDetails user = userDetailsService.loadUserByUsername(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(user);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> getNewToken(@RequestParam String token) {
        String jwt = null;
        if (!jwtUtil.isTokenExpired(token) && jwtUtil.getUsernameFromToken(token) != null) {
            String userName = jwtUtil.getUsernameFromToken(token);
            final UserDetails user = userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(token, user)) {
                jwt = jwtUtil.generateToken(user);
            }
        }
        return ResponseEntity.ok(jwt);
    }

}
