package com.example.wecommerce_api.payload.Purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
//    private Long userId;
    private Long addressDeliveryId;
    private Long productId;
    private Boolean paymentMethod;
    private String remark;
    private String reference;
    private String paidBy;
}
