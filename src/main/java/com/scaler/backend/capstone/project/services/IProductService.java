package com.scaler.backend.capstone.project.services;

import com.scaler.backend.capstone.project.dto.ProductRequestDTO;
import com.scaler.backend.capstone.project.fakestoreapi.FakeStoreProductResponse;
import com.scaler.backend.capstone.project.models.Product;

import java.util.List;

public interface IProductService {
    List<FakeStoreProductResponse> getAllProducts();
    Product getSingleProduct(Long productId);
    Product addNewProduct(ProductRequestDTO productRequestDTO);
    FakeStoreProductResponse updateProduct(Long productId, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long productId);

    FakeStoreProductResponse getProductById(Long productId);

}
