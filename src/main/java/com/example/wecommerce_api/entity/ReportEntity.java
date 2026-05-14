//package com.example.wecommerce_api.entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "report_tb")
//public class ReportEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
//
//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private PostEntity post;
//
//    @Column(name = "reportRemark")
//    private String reportRemark;
//
//    @Column(name = "photo", nullable = false)
//    private String photo;
//
//    @Column(name = "status")
//    private String status;
//
//    @Column(name = "createdDate")
//    private LocalDateTime createdDate;
//
//}
