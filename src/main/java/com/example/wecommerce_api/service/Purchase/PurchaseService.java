package com.example.wecommerce_api.service.Purchase;

import com.example.wecommerce_api.payload.Purchase.PurchaseRequest;
import com.example.wecommerce_api.payload.Receipt.ReceiptResponse;
public interface PurchaseService {
    ReceiptResponse addPurchase(PurchaseRequest purchaseRequest);
}
