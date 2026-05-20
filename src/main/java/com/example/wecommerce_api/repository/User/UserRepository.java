package com.example.wecommerce_api.repository.User;

import com.example.wecommerce_api.entity.UserEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>  {
    @NonNull
    UserEntity getById(@NonNull Integer userId);
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByPhoneNumber(String phoneNumber);
}
