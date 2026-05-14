package com.example.wecommerce_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmark_tb")
public class BookMarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "cratedDate", columnDefinition = "timestamp default now()")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    @JsonIgnore
    private CollectionEntity collection;

}
