package com.example.wecommerce_api.repository.ProductViewCount;

import com.example.wecommerce_api.entity.ProductViewCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductViewCountRepository extends JpaRepository<ProductViewCountEntity,Long> {
    ProductViewCountEntity findByProductId(Long productId);

//    List<ProductViewCountEntity> findTop10ByOrderByCountDesc();
    List<ProductViewCountEntity> findTop10ByProductStatusOrderByCountDesc(String status);

}
