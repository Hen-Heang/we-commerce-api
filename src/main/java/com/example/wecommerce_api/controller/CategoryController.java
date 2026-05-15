package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.service.Category.CategoryService;
import com.example.wecommerce_api.response.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategory(){
        return ResponseEntity.ok(new ApiResponse<>(
                categoryService.getAllCategory(),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestParam String categoryName){
        return ResponseEntity.ok(new ApiResponse<>(
                categoryService.createCategory(categoryName),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
}
