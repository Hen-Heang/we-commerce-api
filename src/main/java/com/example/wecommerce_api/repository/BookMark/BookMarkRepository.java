package com.example.wecommerce_api.repository.BookMark;

import com.example.wecommerce_api.entity.BookMarkEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMarkEntity,Long> {
    @NotNull
    List<BookMarkEntity> findByProduct_TitleContainingIgnoreCaseAndUserId(String title, Integer userId);

    List<BookMarkEntity> findByUserId(Integer userId);
    @NonNull
    BookMarkEntity getById(@NonNull Long bookMarkId);
    BookMarkEntity findByProductIdAndUserId(Long productId,Integer userId);
    
}
