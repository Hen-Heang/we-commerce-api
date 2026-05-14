package com.example.wecommerce_api.service.Notification;

import com.example.wecommerce_api.entity.NotificationEntity;
import com.example.wecommerce_api.payload.Notification.NotificationRequest;
import com.example.wecommerce_api.payload.Notification.NotificationResponse;
import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotification(Integer receiverId,Integer pageNumber,Integer pageSize);

    void deletedNotification(Long id,Integer userId);
}
