package com.example.wecommerce_api.payload.Address;

import com.example.wecommerce_api.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String label;
    private String contact;
    private String telephone;
    private String address;
    private String detail;
}
