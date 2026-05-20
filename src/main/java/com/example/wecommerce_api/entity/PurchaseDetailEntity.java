package com.example.wecommerce_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_detail_tb")
public class PurchaseDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Column(name = "payment_method",columnDefinition = "boolean default false")
    private Boolean paymentMethod;
    @Column(name = "created_date",columnDefinition = "timestamp default now()")
    private LocalDateTime createdDate;
    @Column(name = "remark")
    private  String remark;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToOne(mappedBy = "purchase",cascade = CascadeType.ALL)
    private ReceiptEntity reciept;
    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name = "address_delivery_id")
    private AddressEntity address;

}
