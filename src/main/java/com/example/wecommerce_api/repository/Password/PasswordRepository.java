package com.example.wecommerce_api.repository.Password;

import com.example.wecommerce_api.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity,Long> {
    PasswordEntity findByUserId(Integer userId);
    void deleteAllByUserId(Integer userId);
}
