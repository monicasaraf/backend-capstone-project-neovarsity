package com.scaler.backend.capstone.project.dto;

import com.scaler.backend.capstone.project.models.SortParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private int pageNumber;
    private int sizeOfPage;
    private List<SortParam> sortParamList;
}
