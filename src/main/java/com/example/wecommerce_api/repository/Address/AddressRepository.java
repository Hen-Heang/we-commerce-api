package com.example.wecommerce_api.repository.Address;

import com.example.wecommerce_api.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    List<AddressEntity> findByUserIdOrderByIdDesc(Integer userId);
    AddressEntity getById(Long addressId);
    AddressEntity findByIdAndUserId(Long id,Integer userId);
}
