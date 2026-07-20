package com.example.wecommerce_api.payload.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Despite the class name (kept for wire-compatibility with the
 * /auth/loginPhoneNumber path), {@code identifier} accepts either a phone
 * number or an email address — AuthenticationService tries a phone lookup
 * first, then falls back to email.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLoginRequest {
    @NotBlank
    private String identifier;

    @NotBlank
    private String password;
}