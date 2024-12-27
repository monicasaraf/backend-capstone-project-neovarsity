package com.scaler.backend.capstone.project.services;

import com.scaler.backend.capstone.project.models.Product;
import com.scaler.backend.capstone.project.models.SortParam;
import com.scaler.backend.capstone.project.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProducts(String query, int pageNumber, int sizeOfPage,
                                        List<SortParam> sortParamList) {
//        Sort sort = Sort.by("title").descending()
//                .and(Sort.by("price").descending());

        Sort sort;
        if(sortParamList.get(0).getSortType().equals("ASC")) {
            sort = Sort.by(sortParamList.get(0).getParamName());
        } else {
            sort = Sort.by(sortParamList.get(0).getParamName()).descending();
        }

        for(int i = 1; i< sortParamList.size(); i++) {
            if(sortParamList.get(i).getSortType().equals("ASC")) {
                sort = sort.and(Sort.by(sortParamList.get(i).getParamName()));
            } else {
                sort = sort.and(Sort.by(sortParamList.get(i).getParamName())
                        .descending());
            }
        }

        return productRepository.findByTitleEquals(query, PageRequest.of(pageNumber, sizeOfPage, sort));
    }
}
