package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Enum.Role;
import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.UserDTO;
import com.vincenzolisi.SneakVerse.Repositories.OrderRepository;
import com.vincenzolisi.SneakVerse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUser() {
        List<User> users = repository.findAll();

        List<UserDTO> usersDTO = users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getHomeAddress(),
                        user.getOrders() != null ? user.getOrders().stream().map(o -> o.getOrderId()).toList() : List.of()
                ))
                .toList();
        return usersDTO;
    }

    public UserDTO getUserById(int id) {
        Optional<User> opt = repository.findById(id);

        if (opt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = opt.get();

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setHomeAddress(user.getHomeAddress());
        dto.setRole(user.getRole());
        dto.setOrderId(user.getOrders() != null
                ? user.getOrders().stream().map(Order::getOrderId).toList()
                : List.of());

        return dto;
    }


    public UserDTO addUser(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setHomeAddress(dto.getHomeAddress());
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        } else {
            user.setRole(Role.USER);
        }


        user.setOrders(new ArrayList<>());
        user = repository.save(user);

        dto.setUserId(user.getUserId());
        dto.setRole(user.getRole());
        dto.setOrderId(new ArrayList<>());
        return dto;
    }

    public UserDTO updateUser(int id, UserDTO dto) {
        Optional<User> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = opt.get();

        if(dto.getUsername() != null && !dto.getUsername().isBlank()) {
            user.setUsername(dto.getUsername());
        }

        if(dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if(dto.getHomeAddress() != null) {
            user.setHomeAddress(dto.getHomeAddress());
        }

        if(dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        if(dto.getOrderId() != null) {
            List<Order> updatedOrders = new ArrayList<>();
            for (Integer orderId : dto.getOrderId()) {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
                updatedOrders.add(order);
            }
            user.setOrders(updatedOrders);
        }

        user = repository.save(user);

        UserDTO updto = new UserDTO();
        updto.setUserId(user.getUserId());
        updto.setUsername(user.getUsername());
        updto.setPassword(user.getPassword());
        updto.setHomeAddress(user.getHomeAddress());
        updto.setRole(user.getRole());
        updto.setOrderId(user.getOrders() != null
                ? user.getOrders().stream().map(Order::getOrderId).toList()
                : List.of());

        return updto;
    }

    public boolean deleteUser(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }


}
