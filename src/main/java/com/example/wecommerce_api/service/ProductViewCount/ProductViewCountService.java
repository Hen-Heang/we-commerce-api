package com.example.wecommerce_api.service.ProductViewCount;

import com.example.wecommerce_api.entity.ProductViewCountEntity;
public interface ProductViewCountService {
    ProductViewCountEntity CountView(Long productId);
}
