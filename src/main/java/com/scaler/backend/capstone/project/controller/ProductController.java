package com.scaler.backend.capstone.project.controller;

import com.scaler.backend.capstone.project.dto.ProductRequestDTO;
import com.scaler.backend.capstone.project.dto.ProductResponseDTO;
import com.scaler.backend.capstone.project.fakestoreapi.FakeStoreProductResponse;
import com.scaler.backend.capstone.project.models.Product;
import com.scaler.backend.capstone.project.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        //return productService.getAllProducts();
        List<Product> responseList = productService.getAllProducts();
        List<ProductResponseDTO> productResponseDTO = new ArrayList<>();
        for (Product product : responseList) {
            ProductResponseDTO productResponse = new ProductResponseDTO();
            productResponse.setId(product.getId());
            productResponse.setTitle(product.getTitle());
            productResponse.setPrice(product.getPrice());
            productResponse.setCategory(product.getCategory().getName());
            productResponse.setImageUrl(product.getImageUrl());
            productResponse.setDescription(product.getDescription());
            productResponseDTO.add(productResponse);
        }

        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") Long id) {
        Product data = productService.getProductById(id);
        try{
            ProductResponseDTO productResponse = new ProductResponseDTO();
            productResponse.setId(data.getId());
            productResponse.setTitle(data.getTitle());
            productResponse.setPrice(data.getPrice());
            productResponse.setCategory(data.getCategory().getName());
            if(data==null){
                return new ResponseEntity<>(productResponse, HttpStatus.NO_CONTENT);
            }
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("class-name", "Integrating APIs");
            return new ResponseEntity<>(productResponse, headers,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ProductResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product createdProduct = productService.addNewProduct(productRequestDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.updateProduct(id, productRequestDTO);
        if(product!=null){
            ProductResponseDTO productResponse = new ProductResponseDTO();
            productResponse.setId(product.getId());
            productResponse.setTitle(product.getTitle());
            productResponse.setPrice(product.getPrice());
            productResponse.setCategory(product.getCategory().getName());
            productResponse.setImageUrl(product.getImageUrl());
            productResponse.setDescription(product.getDescription());
            return productResponse;
        }else{
            return null;
        }

    }

    @PatchMapping("/")
    public ResponseEntity<ProductResponseDTO> patchProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        Product response = productService.updateProduct(id, productRequestDTO);
        ProductResponseDTO productResponse = new ProductResponseDTO();
        productResponse.setId(response.getId());
        productResponse.setTitle(response.getTitle());
        productResponse.setPrice(response.getPrice());
        productResponse.setCategory(response.getCategory().getName());
        productResponse.setImageUrl(response.getImageUrl());
        productResponse.setDescription(response.getDescription());

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}
