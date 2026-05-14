package com.example.wecommerce_api.repository.Credencials;

import com.example.wecommerce_api.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredencialRepository extends JpaRepository<CredentialEntity,Long> {
      CredentialEntity findByUserId(Integer userId);
}
