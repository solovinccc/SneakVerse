package com.vincenzolisi.SneakVerse.ModelsDTO.Auth;

public class AuthResponseDTO {
    private String token;
    private String role;
    private Integer userId;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, String role, Integer userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
