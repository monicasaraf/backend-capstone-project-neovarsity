package com.scaler.backend.capstone.project.services;

import com.scaler.backend.capstone.project.dto.ProductRequestDTO;
import com.scaler.backend.capstone.project.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getSingleProduct(Long productId);
    Product addNewProduct((ProductRequestDTO productRequestDTO);
    Product updateProduct(Long productId, (ProductRequestDTO productRequestDTO);
    void deleteProduct(Long productId);
}
