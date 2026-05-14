package com.example.wecommerce_api.payload.Receipt;

import com.example.wecommerce_api.payload.Product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptResponse {
   private ProductResponse productResponse;
   private String reference;
   private LocalDateTime orderDate;
   private String paidBy;
   private String payer;
   private String seller;

}
