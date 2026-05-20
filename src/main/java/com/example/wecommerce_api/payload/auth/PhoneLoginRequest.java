package com.example.wecommerce_api.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLoginRequest {
    @NotBlank
    @Pattern(regexp = "^\\+?[1-9]\\d{6,14}$", message = "Invalid phone number format")
    private String phoneNumber;
}