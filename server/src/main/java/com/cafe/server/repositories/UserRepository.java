package com.cafe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.server.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
