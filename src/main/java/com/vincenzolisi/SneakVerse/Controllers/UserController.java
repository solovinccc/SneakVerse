package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.UserDTO;
import com.vincenzolisi.SneakVerse.Services.UserService;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return service.getAllUser();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id) {
        UserDTO dto = service.getUserById(id);
        if(dto == null) {
            throw new RuntimeException("User with " + id + " id, not found");
        }
        return dto;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto) {
        return service.addUser(dto);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO dto) {
        UserDTO up = service.updateUser(id, dto);
        if(up == null) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = service.deleteUser(id);
        if(!deleted) {
            throw new RuntimeException("User with id " + id + " not found");
        }
        return "User deleted successfully";
    }
}
