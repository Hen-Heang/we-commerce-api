package com.example.wecommerce_api.repository.Receipt;

import com.example.wecommerce_api.entity.ReceiptEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {
    @NonNull
    ReceiptEntity getById(@NonNull Long receiptId);
}
