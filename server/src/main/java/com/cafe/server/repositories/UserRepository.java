package com.cafe.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.server.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findById(int id);

    public User findByEmail(String email);

    @Query("SELECT u.email FROM User u WHERE u.role = :role")
    public List<String> findEmailsByRole(String role);
}
