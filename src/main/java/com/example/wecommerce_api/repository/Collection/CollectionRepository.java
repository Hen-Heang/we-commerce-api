package com.example.wecommerce_api.repository.Collection;

import com.example.wecommerce_api.entity.CollectionEntity;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity,Long> {
    @NonNull
    CollectionEntity getById(@NonNull Long id);

    CollectionEntity findByUserIdAndName(Integer userId, String name);

    List<CollectionEntity> findByUserIdOrderByCreateDateAsc(Integer userId);

    CollectionEntity findByIdAndUserId(Long id,Integer userId);
    List<CollectionEntity> findByNameContainingIgnoreCaseAndUserId(String name,Integer userId);
    void deleteAllByUserId(Integer userid);

}
