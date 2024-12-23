package com.scaler.backend.capstone.project.services;


import com.scaler.backend.capstone.project.clients.fakestore.FakeStoreClient;
import com.scaler.backend.capstone.project.dto.ProductRequestDTO;
import com.scaler.backend.capstone.project.dto.ProductResponseDTO;
import com.scaler.backend.capstone.project.fakestoreapi.FakeStoreProductResponse;
import com.scaler.backend.capstone.project.models.Categories;
import com.scaler.backend.capstone.project.models.Product;
import com.scaler.backend.capstone.project.repository.ProductRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Service
@Primary
@Service
public class EcommStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Override
    public List<Product> getAllProducts() {
        //return productRepository.findAll();
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<Product> productList = new ArrayList<>();
        List<FakeStoreProductResponse> fakeStoreProductDtos = fakeStoreClient.getAllProducts();
        for(int i=0; i<fakeStoreProductDtos.size(); i++) {
            FakeStoreProductResponse fakeStoreProductResponse = fakeStoreProductDtos.get(i);
            Product product = new Product();
            product.setId(Long.valueOf(fakeStoreProductResponse.getId()));
            product.setTitle(fakeStoreProductResponse.getTitle());
            product.setPrice(fakeStoreProductResponse.getPrice());
            Categories category = new Categories();
            category.setName(fakeStoreProductResponse.getCategory());
            product.setCategory(category);
            product.setImageUrl(fakeStoreProductResponse.getImage());
            product.setDescription(fakeStoreProductResponse.getDescription());
            productList.add(product);
        }
        return productList;
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
        return null;
    }

//    @Override
//    public Product updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
//        if (productRepository.existsById(productId)) {
//
//            Product product = new Product();
//            product.setId(productId);
//            product.setTitle(productRequestDTO.getProductName());
//            product.setImageUrl(productRequestDTO.getImageURL());
//            productRequestDTO.setCategory(productRequestDTO.getCategory());
//            productRequestDTO.setPrice(productRequestDTO.getPrice());
//
//            return productRepository.save(product);
//        }
//        return null;
//    }

    public Product updateProduct(Long productId, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        FakeStoreProductResponse fakeStoreProductDto = new FakeStoreProductResponse();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        ResponseEntity<FakeStoreProductResponse> fakeStoreProductDtoResponseEntity
                = requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductResponse.class,
                productId
        );

        FakeStoreProductResponse fakeStoreProductDto1 = fakeStoreProductDtoResponseEntity.getBody();
        return getProduct(fakeStoreProductDto1);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product getProductById(Long productId) {

        /**
         * 1. Call fakestoreAPI
         *  1a. request Object
         *  1b. response object
         */
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductResponse dto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductResponse.class, productId).getBody();
        Product product = new Product();
        product.setId(Long.valueOf(dto.getId()));
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImage());
        product.getCategory().setName(dto.getCategory());
        product.getCategory().setDescription(dto.getDescription());
        return product;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    private Product getProduct(FakeStoreProductResponse productDto) {
        Product product = new Product();
        product.setId(Long.valueOf(productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Categories category = new Categories();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        product.setDescription(productDto.getDescription());
        return product;
    }
}
