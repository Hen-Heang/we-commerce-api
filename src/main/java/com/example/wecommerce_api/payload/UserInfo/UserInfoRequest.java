package com.example.wecommerce_api.payload.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
