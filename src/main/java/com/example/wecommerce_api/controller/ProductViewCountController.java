package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.ProductViewCount.ProductViewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/countView")
public class ProductViewCountController {
private final ProductViewCountService productViewCountService;

    public ProductViewCountController(ProductViewCountService productViewCountService) {
        this.productViewCountService = productViewCountService;
    }

    @PostMapping("/addView")
    public ResponseEntity<?> AddView(@RequestParam Long productId){
        return ResponseEntity.ok(new ApiResponse<>(
                productViewCountService.CountView(productId),
                "OK",
                200,
                false,
                LocalDateTime.now()

        ));
    }
}
