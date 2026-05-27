package com.vincenzolisi.sneakverseauth.Controllers;


import com.vincenzolisi.sneakverseauth.Dto.LoginRequest;
import com.vincenzolisi.sneakverseauth.Dto.RegisterRequest;
import com.vincenzolisi.sneakverseauth.Repositories.SessionRepository;
import com.vincenzolisi.sneakverseauth.Repositories.UserRepository;
import com.vincenzolisi.sneakverseauth.Services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(Map.of("message", "Registration completed"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        Map<String, Object> response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = authService.checkEmail(email);
        return ResponseEntity.ok(Map.of("exists", exists));
    }



}
