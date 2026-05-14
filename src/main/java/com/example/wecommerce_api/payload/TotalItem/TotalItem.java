package com.example.wecommerce_api.payload.TotalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalItem {
    private Integer totalItemSelling;
    private Integer totalItemPurchased;
    private Integer totalItemSoldOut;
    private Integer totalAllItem;

}
