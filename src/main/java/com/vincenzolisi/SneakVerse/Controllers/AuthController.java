package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.ModelsDTO.Auth.AuthResponseDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.Auth.LoginRequestDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.RegisterDTO;
import com.vincenzolisi.SneakVerse.Services.AuthService;
import com.vincenzolisi.SneakVerse.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO dto) {
        authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return loginService.login(dto);
    }
}
