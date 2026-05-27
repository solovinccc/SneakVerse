package com.vincenzolisi.sneakverseauth.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    private String password;

    private String firstName;
    private String lastName;
    private String homeAddress;
}
