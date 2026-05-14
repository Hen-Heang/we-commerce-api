package com.example.wecommerce_api.payload.BookMark;

import com.example.wecommerce_api.entity.BookMarkEntity;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMarkResponse {
private Long id;
private ProductResponse product;
private String status;
private LocalDateTime createdDate;

}
