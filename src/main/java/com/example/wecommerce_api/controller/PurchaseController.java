package com.example.wecommerce_api.controller;


import com.example.wecommerce_api.payload.Purchase.PurchaseRequest;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.Product.ProductService;
import com.example.wecommerce_api.service.Purchase.PurchaseService;
import com.example.wecommerce_api.service.Receipt.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final ReceiptService receiptService;
    private final ProductService productService;


    @PostMapping
    public ResponseEntity<?> Addpurchase(@RequestBody PurchaseRequest purchaseRequest) {
        return ResponseEntity.ok(new ApiResponse<>(
                purchaseService.addPurchase(purchaseRequest),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @GetMapping("receipt/{id}")
    public ResponseEntity<?> getReceiptById(@PathVariable("id") Long receiptId) {
        return ResponseEntity.ok(new ApiResponse<>(
                receiptService.getReceiptById(receiptId),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @PutMapping("status/{productId}")
    public ResponseEntity<?> UpdateStatus(@PathVariable Long productId) {
        productService.UpdateStatus(productId);
        return ResponseEntity.ok(new ApiResponse<>(
                "Updated Success!",
                "OK",
                200,
                false,
                LocalDateTime.now()

        ));
    }
}
