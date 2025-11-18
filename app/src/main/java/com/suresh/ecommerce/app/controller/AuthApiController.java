package com.suresh.ecommerce.app.controller;

import com.suresh.api.AuthApi;
import com.suresh.api.model.LoginRequest;
import com.suresh.api.model.LoginUser200Response;
import com.suresh.api.model.RegisterRequest;
import com.suresh.ecommerce.app.user.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AuthApiController implements AuthApi {

    private final AuthService authService;

    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<Void> registerUser(RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<LoginUser200Response> loginUser(LoginRequest loginRequest) {
        LoginUser200Response token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }
}
