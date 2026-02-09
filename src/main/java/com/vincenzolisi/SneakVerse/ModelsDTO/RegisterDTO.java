package com.vincenzolisi.SneakVerse.ModelsDTO;

public class RegisterDTO {

    private String username;
    private String password;
    private String homeAddress;
    private String adminKey;

    public RegisterDTO() { }

    public RegisterDTO(String username, String password, String homeAddress, String adminKey) {
        this.username = username;
        this.password = password;
        this.homeAddress = homeAddress;
        this.adminKey = adminKey;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }

    public String getAdminKey() { return adminKey; }
    public void setAdminKey(String adminKey) { this.adminKey = adminKey; }
}
