package com.example.wecommerce_api.repository.Purchase;

import com.example.wecommerce_api.entity.PurchaseDetailEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRpository extends JpaRepository<PurchaseDetailEntity,Long> {
   List<PurchaseDetailEntity> findByUserIdOrderByCreatedDateDesc(Integer userId, PageRequest pageRequest);
   List<PurchaseDetailEntity> getByUserId(Integer userId);
   List<PurchaseDetailEntity> findAllByUserId(Integer userId);

}
