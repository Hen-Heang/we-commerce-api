package com.example.wecommerce_api.repository.DeviceToken;

import com.example.wecommerce_api.entity.DeviceTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceTokenEntity,Long> {
    DeviceTokenEntity findByUserId(Integer userId);
}
