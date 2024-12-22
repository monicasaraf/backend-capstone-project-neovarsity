package com.scaler.backend.capstone.project.services;


import com.scaler.backend.capstone.project.dto.ProductRequestDTO;
import com.scaler.backend.capstone.project.models.Product;
import com.scaler.backend.capstone.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@Service
@Primary
@Service
public class EcommStoreProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product addNewProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setTitle(productRequestDTO.getProductName());
        product.setImageUrl(productRequestDTO.getImageURL());
        productRequestDTO.setCategory(productRequestDTO.getCategory());
        productRequestDTO.setPrice(productRequestDTO.getPrice());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        if (productRepository.existsById(productId)) {

            Product product = new Product();
            product.setId(productId);
            product.setTitle(productRequestDTO.getProductName());
            product.setImageUrl(productRequestDTO.getImageURL());
            productRequestDTO.setCategory(productRequestDTO.getCategory());
            productRequestDTO.setPrice(productRequestDTO.getPrice());

            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
