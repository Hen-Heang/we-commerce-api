package com.example.wecommerce_api.repository.Photo;

import com.example.wecommerce_api.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
    List<PhotoEntity> findByProductId(Long productId);
}
