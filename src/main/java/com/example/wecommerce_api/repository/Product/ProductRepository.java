package com.example.wecommerce_api.repository.Product;

import com.example.wecommerce_api.entity.ProductEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitleContainingIgnoreCase(String title, PageRequest pageRequest);

    List<ProductEntity> findAllByUser_Id(Integer userId, PageRequest pageRequest);

    List<ProductEntity> findAllByStatusAndUser_IdOrderByCreatedDateDesc(String status, Integer userId, PageRequest pageRequest);

    @NonNull
    Optional<ProductEntity> findById(@NonNull Long productId);

    @NonNull
    ProductEntity getById(@NonNull Long productId);

    List<ProductEntity> findAllByOrderByCreatedDateDesc(PageRequest pageRequest);

    List<ProductEntity> findAllByStatusAndUser_Id(String status, Integer userId);

    List<ProductEntity> findAllByUser_Id(Integer userId);

    ProductEntity findByIdAndStatus(Long proId, String status);

    List<ProductEntity> findAllByCategory_CategoryName(String categoryName, PageRequest pageRequest);
}
