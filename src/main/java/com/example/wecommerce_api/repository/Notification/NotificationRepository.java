package com.example.wecommerce_api.repository.Notification;

import com.example.wecommerce_api.entity.NotificationEntity;
import com.example.wecommerce_api.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
    List<NotificationEntity> findByReceiverOrderByCreatedDateDesc(UserEntity receiver, PageRequest pageRequest);
    NotificationEntity findByReceiverIdAndId(Integer userId,Long id);
}
