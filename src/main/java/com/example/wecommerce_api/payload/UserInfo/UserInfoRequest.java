package com.example.wecommerce_api.payload.UserInfo;

import com.example.wecommerce_api.entity.PhotoEntity;
import com.example.wecommerce_api.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest  {
    private String userName;
    private String phoneNumber;
    private String address;
    private String photoProfile;
    private String maplink;
}
