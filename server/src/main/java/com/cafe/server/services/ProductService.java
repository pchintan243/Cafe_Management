package com.cafe.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.server.entities.Category;
import com.cafe.server.entities.Product;
import com.cafe.server.entities.ProductDao;
import com.cafe.server.repositories.CategoryRepository;
import com.cafe.server.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product addNewProduct(ProductDao dao) {
        int categoryId = dao.getCategoryId();
        Category categoryDetails = categoryRepository.findById(categoryId);
        if (categoryDetails != null) {
            Product product = new Product();
            product.setName(dao.getName());
            product.setPrice(dao.getPrice());
            product.setDescription(dao.getDescription());
            product.setCategory(categoryDetails);
            product.setStatus("true");
            return productRepository.save(product);
        }
        return null;
    }

    public Product updateProduct(ProductDao dao, int id) {
        Product getProduct = productRepository.findById(id);

        int categoryId = dao.getCategoryId();
        Category categoryDetails = categoryRepository.findById(categoryId);

        if (getProduct != null && categoryDetails != null) {
            getProduct.setName(dao.getName());
            getProduct.setPrice(dao.getPrice());
            getProduct.setDescription(dao.getDescription());

            getProduct.setCategory(categoryDetails);

            return productRepository.save(getProduct);
        } else {
            return null;
        }
    }

    public void deleteProduct(int id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            productRepository.delete(product);
        }
    }
}
