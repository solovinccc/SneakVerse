package com.vincenzolisi.sneakverseauth.Services;

import com.vincenzolisi.sneakverseauth.Dto.LoginRequest;
import com.vincenzolisi.sneakverseauth.Dto.RegisterRequest;
import com.vincenzolisi.sneakverseauth.Models.Enum.Role;
import com.vincenzolisi.sneakverseauth.Models.User;
import com.vincenzolisi.sneakverseauth.Models.UserSession;
import com.vincenzolisi.sneakverseauth.Repositories.SessionRepository;
import com.vincenzolisi.sneakverseauth.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already signed in");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setHomeAddress(request.getHomeAddress());
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public Map<String, Object> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Wrong email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong email or password");
        }

        String token = UUID.randomUUID().toString();

        UserSession session = UserSession.builder()
                .token(token)
                .userId(user.getUserId())
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
        sessionRepository.save(session);
        return Map.of("token", token, "userId", user.getUserId());
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Map<String, Object> validateToken(String token) {
        UserSession session = sessionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        User user = userRepository.findById(session.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Map.of(
                "userId", user.getUserId(),
                "role", user.getRole().name()
        );
    }

    public void logout(String token) {
        sessionRepository.delete(token);
    }


}
