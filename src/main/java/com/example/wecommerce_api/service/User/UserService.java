package com.example.wecommerce_api.service.User;

import com.example.wecommerce_api.entity.CredentialEntity;
import com.example.wecommerce_api.entity.DeviceTokenEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.UserInfo.UserInfoRequest;

import java.util.Optional;

public abstract class UserService {
    public abstract String EditUser(UserInfoRequest request, Integer userId);
    public abstract Optional<UserEntity> GetUserInfor(Integer userId);
    public abstract void deleteUser(Integer userId);
    public abstract Optional<UserEntity> getUserByEmail(String email);
    public abstract void VerifyPass(String password, Integer id) throws Exception;
    public abstract void ConnectWebill(CredentialEntity credential);
    public abstract void DisConnect();
    public abstract CredentialEntity getCredentials(Integer userId);
    public abstract void InsertDeviceToken(String deviceToken);
    public abstract DeviceTokenEntity getDeviceToken(Integer userId);
}
