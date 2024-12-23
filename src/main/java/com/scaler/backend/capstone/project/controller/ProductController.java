package com.scaler.backend.capstone.project.controller;

import com.scaler.backend.capstone.project.dto.ProductRequestDTO;
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

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;


    @GetMapping
    public ResponseEntity<List<FakeStoreProductResponse>> getProducts() {
        //return productService.getAllProducts();
        List<FakeStoreProductResponse> responseList = productService.getAllProducts();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FakeStoreProductResponse> getProduct(@PathVariable("id") Long id) {
        FakeStoreProductResponse data = productService.getProductById(id);
        try{

            if(data==null){
                return new ResponseEntity<>(data, HttpStatus.NO_CONTENT);
            }
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("class-name", "Integrating APIs");
            return new ResponseEntity<>(data, headers,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        Product createdProduct = productService.addNewProduct(productRequestDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public FakeStoreProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id, productRequestDTO);
    }

    @PatchMapping("/")
    public ResponseEntity<FakeStoreProductResponse> patchProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        FakeStoreProductResponse response = productService.updateProduct(id, productRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}
