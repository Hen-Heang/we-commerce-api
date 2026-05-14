package com.example.wecommerce_api.service.Receipt;

import com.example.wecommerce_api.payload.Receipt.ReceiptResponse;
public interface ReceiptService {

    ReceiptResponse getReceiptById(Long receiptId);
}
