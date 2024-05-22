package com.cafe.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.server.entities.Category;
import com.cafe.server.services.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/getAllCategory")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @PostMapping("/category/addCategory")
    public ResponseEntity<Category> addNewCategory(@RequestBody Category category) {

        Category newCategory = categoryService.addNewCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/category/updateCategory/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable int id) {
        Category updateCategory = categoryService.updateCategory(category, id);
        if (updateCategory != null) {
            return ResponseEntity.ok(updateCategory);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/category/deleteCategory/{id}")
    public <T> ResponseEntity<T> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
