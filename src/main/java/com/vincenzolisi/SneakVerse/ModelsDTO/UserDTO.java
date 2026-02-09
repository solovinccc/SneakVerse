package com.vincenzolisi.SneakVerse.ModelsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vincenzolisi.SneakVerse.Models.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Integer userId;

    private String username;

    private String password;

    private String homeAddress;

    private List<Integer> orderId = new ArrayList<>();

    private Role role;

    public UserDTO() {  }

    public UserDTO(Integer userId, String username, String password, String homeAddress, List<Integer> orderId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.homeAddress = homeAddress;
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
