package com.example.wecommerce_api.service.Purchase;

import com.example.wecommerce_api.payload.Purchase.PurchaseRequest;
import com.example.wecommerce_api.payload.Receipt.ReceiptResponse;
import jakarta.transaction.Transactional;

public interface PurchaseService {
    @Transactional

    ReceiptResponse addPurchase(PurchaseRequest purchaseRequest);
}
