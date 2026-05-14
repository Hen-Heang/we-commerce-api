package com.example.wecommerce_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "code_tb")
public class CodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "pin_code")
    private String pinCode;
    @Column(name = "created_date", columnDefinition = "timestamp default now()")
    private LocalDateTime createDate;
    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expireDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
