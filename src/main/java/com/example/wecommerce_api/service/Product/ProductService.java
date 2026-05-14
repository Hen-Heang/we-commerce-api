package com.example.wecommerce_api.service.Product;

import com.example.wecommerce_api.entity.ProductEntity;
import com.example.wecommerce_api.payload.Product.ProductRequest;
import com.example.wecommerce_api.payload.Product.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> getAllPost(int pageNumber, int pageSize);
    Optional<ProductEntity> getPostById(Long productId);
    List<ProductResponse> getPostByCategoryName(String categoryName, Integer pageNumber, Integer pageSize);
    List<ProductResponse> getPostByTitle(String title, Integer pageNumber, Integer pageSize);
    ProductResponse PostProduct(ProductRequest productRequest, String categoryName);
    List<ProductResponse> getTopItems();
    List<ProductResponse> getAllItemByUserId(Integer userId, Integer pageNumber, Integer pageSize);
    List<ProductResponse> getAllItemSelling(Integer userId, Integer pageNumber, Integer pageSize);
    List<ProductResponse> getAllItemPurchased(Integer userId, Integer pageNumber, Integer pageSize);
    List<ProductResponse> getAllItemSoldOut(Integer userId, Integer pageNumber, Integer pageSize);
    void HideProduct(Long productId);
    Integer getSize(String condition, Integer userId);
    void UpdateStatus(Long productId);
}
