package com.example.wecommerce_api.payload.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String userName;
    private String companyName;
    private Boolean status;
    private String createdAt;
    private String createdBy;
    private String modifiedAt;
    private String modifiedBy;
}
