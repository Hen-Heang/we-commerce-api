package com.example.wecommerce_api.payload.auth;

import com.example.wecommerce_api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String userName;
  private String email;
  private String password;
  private String phoneNumber;
  private String address;
  private String photoProfile;
  private Role role;
  private String googleLink;
  private String mapLink;
}
