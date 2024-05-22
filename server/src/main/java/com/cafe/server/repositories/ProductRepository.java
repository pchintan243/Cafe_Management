package com.cafe.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe.server.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    List<Product> findByCategoryId(@Param("id") int id);
}
