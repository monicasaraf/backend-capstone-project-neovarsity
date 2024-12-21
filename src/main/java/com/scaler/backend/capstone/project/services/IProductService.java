package com.scaler.backend.capstone.project.services;

import com.scaler.backend.capstone.project.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getSingleProduct(Long productId);
    Product addNewProduct(Product product);
    Product updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
}
