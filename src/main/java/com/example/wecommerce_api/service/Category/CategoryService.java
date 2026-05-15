package com.example.wecommerce_api.service.Category;

import com.example.wecommerce_api.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAllCategory();
    CategoryEntity createCategory(String categoryName);
}
