package com.example.wecommerce_api.payload.Login;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
