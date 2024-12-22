package com.scaler.backend.capstone.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
       private String productName;
       private String Category;
       private String imageURL;
       private double price;

}
