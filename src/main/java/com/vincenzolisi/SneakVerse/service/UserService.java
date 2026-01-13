package com.vincenzolisi.SneakVerse.service;

import com.vincenzolisi.SneakVerse.entity.User;
import com.vincenzolisi.SneakVerse.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo)
    {
        this.repo = repo;
    }

    public User register (User user) {

    }

}
