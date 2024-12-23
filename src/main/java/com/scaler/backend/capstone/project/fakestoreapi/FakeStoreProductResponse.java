package com.scaler.backend.capstone.project.fakestoreapi;

import lombok.Data;

@Data
public class FakeStoreProductResponse {

    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;

    private Rating rating;



}