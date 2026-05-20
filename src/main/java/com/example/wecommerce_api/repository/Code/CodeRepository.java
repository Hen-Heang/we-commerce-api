package com.example.wecommerce_api.repository.Code;

import com.example.wecommerce_api.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    Optional<CodeEntity> findByPhoneNumber(String phoneNumber);
}