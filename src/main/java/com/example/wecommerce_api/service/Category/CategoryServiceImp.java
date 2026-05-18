package com.example.wecommerce_api.service.Category;

import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.CategoryEntity;
import com.example.wecommerce_api.repository.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryServiceImp implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getAllCategory() {
        if (categoryRepository.findAllByOrderById().isEmpty()){
            throw new NotFoundExceptionHandler("No record!");
        }
       List<CategoryEntity> categorys = categoryRepository.findAllByOrderById();
        return categorys;
    }

    @Override
    public CategoryEntity createCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("categoryName is required");
        }
        // Idempotent: return the existing row if a category with this name exists.
        CategoryEntity existing = categoryRepository.getByCategoryName(categoryName.trim());
        if (existing != null) {
            return existing;
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryName(categoryName.trim());
        return categoryRepository.save(entity);
    }
}
