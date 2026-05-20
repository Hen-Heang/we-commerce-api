package com.example.wecommerce_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reciept_tb")
public class ReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reference")
    private String reference;
    @Column(name = "paid_by")
    private String paidBy;
    @Column(name = "paid_date")
    private LocalDateTime paidDate;
    @OneToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseDetailEntity purchase;
    @OneToMany(mappedBy = "reciept",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NotificationEntity> notification;
}
