package com.vincenzolisi.SneakVerse.ModelsDTO;

import com.vincenzolisi.SneakVerse.Models.Enum.Role;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Integer userId;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String homeAddress;

    private List<Integer> orderId = new ArrayList<>();

    private Role role;

    public UserDTO() {  }

    public UserDTO(Integer userId, String password, String email, String firstName, String lastName, String homeAddress, List<Integer> orderId) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.homeAddress = homeAddress;
        this.orderId = orderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<Integer> getOrderId() {
        return orderId;
    }

    public void setOrderId(List<Integer> orderIds) {
        this.orderId = orderIds;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
