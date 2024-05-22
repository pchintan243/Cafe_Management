package com.cafe.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.server.entities.Category;
import com.cafe.server.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category addNewCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category, int id) {
        Category getCategory = categoryRepository.findById(id);

        if (getCategory != null) {
            getCategory.setName(category.getName());
            return categoryRepository.save(getCategory);
        } else {
            return null;
        }
    }

    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id);
        if (category != null) {
            categoryRepository.delete(category);
        }
    }
}
