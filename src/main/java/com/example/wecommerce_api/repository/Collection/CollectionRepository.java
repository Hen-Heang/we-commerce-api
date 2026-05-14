package com.example.wecommerce_api.repository.Collection;

import com.example.wecommerce_api.entity.CollectionEntity;
import com.example.wecommerce_api.entity.UserEntity;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity,Long> {
    CollectionEntity getById(Long id);

    CollectionEntity findByUserIdAndName(Integer userId, String name);

    List<CollectionEntity> findByUserIdOrderByCreateDateAsc(Integer userId);

    CollectionEntity findByIdAndUserId(Long id,Integer userId);
    List<CollectionEntity> findByNameContainingIgnoreCaseAndUserId(String name,Integer userId);
    void deleteAllByUserId(Integer userid);

}
