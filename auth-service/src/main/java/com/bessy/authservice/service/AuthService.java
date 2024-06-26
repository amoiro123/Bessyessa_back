package com.bessy.authservice.service;

import com.bessy.authservice.client.UserServiceClient;
import com.bessy.authservice.dto.RegisterDto;
import com.bessy.authservice.dto.TokenDto;
import com.bessy.authservice.exc.WrongCredentialsException;
import com.bessy.authservice.request.LoginRequest;
import com.bessy.authservice.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public TokenDto login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));
        if (authenticate.isAuthenticated())
            return TokenDto
                    .builder()
                    .token(jwtService.generateToken(request.getUsername()))
                    .build();
        else throw new WrongCredentialsException("Wrong credentials");
    }

    public RegisterDto register(RegisterRequest request) {
        return userServiceClient.save(request).getBody();
    }
}
