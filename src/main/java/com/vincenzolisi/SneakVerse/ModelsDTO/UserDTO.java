package com.vincenzolisi.SneakVerse.ModelsDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;


public class UserDTO {

    private Integer userId;
    private String username;
    private String password;
    private String homeAddress;
    private List<Integer> orderIds = new ArrayList<>();

    public UserDTO() {  }

    public UserDTO(Integer userId, String username, String password, String homeAddress, List<Integer> orderIds) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.homeAddress = homeAddress;
        this.orderIds = orderIds;
    }

    @JsonIgnore
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
        return orderIds;
    }

    public void setOrderId(List<Integer> orderIds) {
        this.orderIds = orderIds;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", orderIds=" + orderIds +
                '}';
    }
}
