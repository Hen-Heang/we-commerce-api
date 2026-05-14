package com.example.wecommerce_api.service.Category;

import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.CategoryEntity;
import com.example.wecommerce_api.repository.Category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        if (categoryRepository.findAllByOrderById().isEmpty()){
            throw new NotFoundExceptionHandler("No record!");
        }
       List<CategoryEntity> categorys = categoryRepository.findAllByOrderById();
        return categorys;
    }
}
