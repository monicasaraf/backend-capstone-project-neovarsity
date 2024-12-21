package com.scaler.backend.capstone.project.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String title;
    private double price;
    private String description;
    @ManyToOne
    @JsonBackReference
    private Categories category;
    private String imageUrl;
    private Boolean isPublic;
    private int numberOfUnits;
}
