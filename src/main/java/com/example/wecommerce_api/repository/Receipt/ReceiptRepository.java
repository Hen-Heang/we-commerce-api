package com.example.wecommerce_api.repository.Receipt;

import com.example.wecommerce_api.entity.RecieptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<RecieptEntity,Long> {
RecieptEntity getById(Long receiptId);
}
