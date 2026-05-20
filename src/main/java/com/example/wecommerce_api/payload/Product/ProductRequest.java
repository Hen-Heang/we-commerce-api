package com.example.wecommerce_api.payload.Product;

import com.example.wecommerce_api.entity.PhotoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private Double price;
    private Double discountValues;
    private Boolean discountType;
    private String condition;
    private String brand;
    private String model;
    private String color;
    private String year;
    private String size;
    private String type;
    private List<PhotoEntity> photo;
}
