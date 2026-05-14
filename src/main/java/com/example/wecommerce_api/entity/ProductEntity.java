package com.example.wecommerce_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_tb")
@Data
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "isHide", columnDefinition = "boolean default false")
    private Boolean isHide;

    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "description",length = 550)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "totalAmount")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double totalAmount;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "discount_values")
    private Double discountValues;

    @Column(name = "discount_type")
    private Boolean discountType;
    @Column(name = "created_date", columnDefinition = "timestamp default now()")
    private LocalDateTime createdDate;
    @Column(name = "condition")
    private String codition;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "color")
    private String color;
    @Column(name = "year")
    private String year;
    @Column(name = "size")
    private String size;
    @Column(name = "type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserEntity user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookMarkEntity> bookMark;

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnore
    private PurchaseDetailEntity purchase;


   @OneToOne(mappedBy = "product",cascade = CascadeType.ALL)
   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   @JsonIgnore
    private ProductViewCountEntity productViewCount;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<PhotoEntity> photo;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CategoryEntity category;
}
