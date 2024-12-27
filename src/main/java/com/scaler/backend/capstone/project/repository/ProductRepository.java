package com.scaler.backend.capstone.project.repository;

import com.scaler.backend.capstone.project.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitleEquals(String title, Pageable pageable);
}
