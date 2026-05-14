package com.example.wecommerce_api.payload.BookMark;

import com.example.wecommerce_api.entity.PhotoEntity;
import com.example.wecommerce_api.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMarkDetailResponse {
    private Long id;
    private String title;
    private Double price;
    private String status;
    private Double discountValues;
    private Boolean discountType;
    private LocalDateTime createdDate;
    private String codition;
    private String brand;
    private String model;
    private String color;
    private String year;
    private String size;
    private String type;
    private Double totalAmount;
    private List<PhotoEntity> photo;
    private UserEntity user;

}
