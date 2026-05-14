package com.example.wecommerce_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "notification_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "contanct")
    private String contanct;
    @Column(name = "is_read",columnDefinition = "boolean default false" )
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @Column(name = "created_date",columnDefinition = "timestamp default now()")
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "reciept_id")
    private RecieptEntity reciept;
}
