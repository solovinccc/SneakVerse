package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Enum.Role;
import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.RegisterDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.UserDTO;
import com.vincenzolisi.SneakVerse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String ADMIN_KEY = "admin"; // chiave per diventare admin

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO register(RegisterDTO dto) {

        if(userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username già esistente!");
        }


        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setHomeAddress(dto.getHomeAddress());


        if(dto.getAdminKey() != null && dto.getAdminKey().equals(ADMIN_KEY)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }


        userRepository.save(user);


        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setHomeAddress(user.getHomeAddress());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
