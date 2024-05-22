package com.cafe.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.server.entities.Product;
import com.cafe.server.entities.ProductDao;
import com.cafe.server.services.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/getAllProduct")
    public ResponseEntity<List<Product>> getAllCategories() {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping("/product/addProduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDao productDao) {

        Product newProduct = productService.addNewProduct(productDao);
        if (newProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/product/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDao productDao, @PathVariable int id) {
        Product updateProduct = productService.updateProduct(productDao, id);
        if (updateProduct != null) {
            return ResponseEntity.ok(updateProduct);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/deleteProduct/{id}")
    public <T> ResponseEntity<T> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product/getProduct/{id}")
    public ResponseEntity<List<Product>> getProduct(@PathVariable int id) {
        List<Product> product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
