package com.example.wecommerce_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "product_view_count_tb")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductViewCountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

   @OneToOne
   @JoinColumn(name = "product_id")
   @JsonIgnore
    private ProductEntity product;
    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "last_view_date",columnDefinition = "timestamp default now()")
    private LocalDateTime lastViewDate;

}
