package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.UserDTO;
import com.vincenzolisi.SneakVerse.Repositories.OrderRepository;
import com.vincenzolisi.SneakVerse.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        UserDTO dto = null;

        if(opt.isPresent()) {
            User user = opt.get();
            dto = new UserDTO();

            dto.setUserId(user.getUserId());
            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            //aggiungo gli orderid
            List<Integer> orderIds = new ArrayList<>();
            if(user.getOrders() != null) {
                for(Order order : user.getOrders()) {
                    orderIds.add(order.getOrderId());
                }
            }
            dto.setOrderId(orderIds);
        }

        return dto;
    }

    public UserDTO addUser(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setHomeAddress(dto.getHomeAddress());
        user.setOrders(new ArrayList<>());

        user = repository.save(user);
        //restiusco il dto aggiornato con l'id generato
        dto.setUserId(user.getUserId());
        dto.setOrderId(new ArrayList<>()); //lista vuota se non ci sono ordini
        return dto;
    }

    public UserDTO updateUser(int id, UserDTO dto) {
        Optional<User> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = opt.get();

        if(dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if(dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }

        if(dto.getHomeAddress() != null) {
            user.setHomeAddress(dto.getHomeAddress());
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
        updto.setOrderId(user.getOrders() != null
            ? user.getOrders().stream().map(Order::getOrderId) .toList()
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
