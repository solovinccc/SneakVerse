package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.Auth.AuthResponseDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.Auth.LoginRequestDTO;
import com.vincenzolisi.SneakVerse.Repositories.UserRepository;
import com.vincenzolisi.SneakVerse.Configs.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponseDTO login(LoginRequestDTO dto) {

        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());

        if (optionalUser.isEmpty()) {

            throw new IllegalArgumentException("Username o password non corretti");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Username o password non corretti");
        }

        //Genero il token
        String token = jwtUtils.generateToken(dto.getUsername());

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setRole(user.getRole().name());
        response.setUserId(user.getUserId());

        return response;
    }
}
