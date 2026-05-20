package com.example.wecommerce_api.service.ProductViewCount;

import com.example.wecommerce_api.exception.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.ProductEntity;
import com.example.wecommerce_api.entity.ProductViewCountEntity;
import com.example.wecommerce_api.exception.exceptionValidateInput.Validation;
import com.example.wecommerce_api.repository.Product.ProductRepository;
import com.example.wecommerce_api.repository.ProductViewCount.ProductViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ProductViewCountServiceImp implements ProductViewCountService{
    private final ProductViewCountRepository productViewCountRepository;
    private final ProductRepository productRepository;
    private final Validation validation;
    int count = 1;

    @Override
    public ProductViewCountEntity CountView(Long productId) {
        if(productRepository.findById(productId).isEmpty()){
            throw new NotFoundExceptionHandler("Product not found!");
        }
        ProductEntity product = productRepository.getById(productId);
        ProductViewCountEntity productViewCount = new ProductViewCountEntity();
        productViewCount.setProduct(product);
        productViewCount.setLastViewDate(LocalDateTime.now());
        if (productViewCountRepository.findByProductId(productId) != null) {
            productViewCount = productViewCountRepository.findByProductId(productId);

                count = productViewCount.getCount() + 1;
        }
        productViewCount.setCount(count);
        return productViewCountRepository.save(productViewCount);
    }
}
