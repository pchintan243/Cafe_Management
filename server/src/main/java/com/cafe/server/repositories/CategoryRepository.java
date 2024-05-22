package com.cafe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.server.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);
}
