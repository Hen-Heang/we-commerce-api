package com.example.wecommerce_api.payload.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
  private Long id;
  private String contant;
  private String decription;
  private LocalDateTime createdDate;
  private String receiverPhoto;
  private Long receiptId;
}
