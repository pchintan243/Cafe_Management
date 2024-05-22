package com.cafe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.server.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
}
