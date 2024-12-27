package com.scaler.backend.capstone.project.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @JsonManagedReference
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> productList;
}

// Mapped is available for one to one, one to many and many to many