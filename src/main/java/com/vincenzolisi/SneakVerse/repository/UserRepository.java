package com.vincenzolisi.SneakVerse.repository;

import com.vincenzolisi.SneakVerse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
