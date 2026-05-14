package com.example.wecommerce_api.payload.Product;

import com.example.wecommerce_api.entity.PhotoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private Double price;
    private String status;
    private Boolean isSaved;
    private LocalDateTime createdDate;

    private Double totalAmount;
    private List<PhotoEntity> photo;
}
