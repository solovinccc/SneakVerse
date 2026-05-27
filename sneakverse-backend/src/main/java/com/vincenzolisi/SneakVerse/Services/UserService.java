package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.UserDTO;
import com.vincenzolisi.SneakVerse.Repositories.OrderRepository;
import com.vincenzolisi.SneakVerse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private OrderRepository orderRepository;



    public List<UserDTO> getAllUser() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UserDTO getUserById(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = repository.findByEmail(email);

        if(userOptional.isEmpty()) {
            return null;
        }

        return mapToDTO(userOptional.get());
    }


    public UserDTO saveOrUpdate(UserDTO dto) {

        User existingUser = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

            if(dto.getHomeAddress() != null && !dto.getHomeAddress().isBlank()) {
                existingUser.setHomeAddress(dto.getHomeAddress());
            }
            if(dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
                existingUser.setFirstName(dto.getFirstName());
            }
            if(dto.getLastName() != null && !dto.getLastName().isBlank()) {
                existingUser.setLastName(dto.getLastName());
            }
            if(dto.getRole() != null) {
                existingUser.setRole(dto.getRole());
            }

            existingUser = repository.save(existingUser);
            return mapToDTO(existingUser);
    }

    public UserDTO updateUser(int id, UserDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getHomeAddress() != null) {
            user.setHomeAddress(dto.getHomeAddress());
        }
        if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            user.setLastName(dto.getLastName());
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        if (dto.getOrderId() != null) {
            List<Order> updatedOrders = new ArrayList<>();
            for (Integer orderId : dto.getOrderId()) {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
                updatedOrders.add(order);
            }
            user.setOrders(updatedOrders);
        }

        user = repository.save(user);
        return mapToDTO(user);
    }

    public boolean deleteUser(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setHomeAddress(user.getHomeAddress());

        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPassword(null);

        dto.setRole(user.getRole());
        dto.setOrderId(user.getOrders() != null
                ? user.getOrders().stream().map(Order::getOrderId).toList()
                : List.of());

        return dto;
    }
}