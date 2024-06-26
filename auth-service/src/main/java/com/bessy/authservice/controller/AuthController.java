package com.bessy.authservice.controller;

import com.bessy.authservice.dto.RegisterDto;
import com.bessy.authservice.dto.TokenDto;
import com.bessy.authservice.request.LoginRequest;
import com.bessy.authservice.request.RegisterRequest;
import com.bessy.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
