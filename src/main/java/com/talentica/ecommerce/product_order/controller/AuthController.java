package com.talentica.ecommerce.product_order.controller;

import com.talentica.ecommerce.product_order.model.*;
import com.talentica.ecommerce.product_order.repository.*;
import com.talentica.ecommerce.product_order.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("CUSTOMER").build()));

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRoles(Set.of(customerRole));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(
                user.getUsername(),
                user.getRoles().stream().map(Role::getName).toList()
        );

        return ResponseEntity.ok(token);
    }
}
