package com.scaler.backend.capstone.project.clients.fakestore;

import com.scaler.backend.capstone.project.fakestoreapi.FakeStoreProductResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component

public class FakeStoreClient {
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<FakeStoreProductResponse> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductResponse[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductResponse[].class
        );

        return Arrays.asList(l.getBody());
    }

    public FakeStoreProductResponse getSingleProduct(Long productId) {
        // TODO
        return null;
    }
}
