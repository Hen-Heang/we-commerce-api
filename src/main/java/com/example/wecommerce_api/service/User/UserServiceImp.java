package com.example.wecommerce_api.service.User;

import com.example.wecommerce_api.entity.CredentialEntity;
import com.example.wecommerce_api.entity.DeviceTokenEntity;
import com.example.wecommerce_api.entity.token.Token;
import com.example.wecommerce_api.entity.token.TokenRepository;
import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
//import com.example.wecommerce_api.entity.BankEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.enums.ResponseMessage;
import com.example.wecommerce_api.exception.CustomExceptionSecurity;
import com.example.wecommerce_api.exception.exceptionValidateInput.Validation;
import com.example.wecommerce_api.payload.UserInfo.UserInfoRequest;
import com.example.wecommerce_api.repository.Collection.CollectionRepository;
import com.example.wecommerce_api.repository.Credencials.CredencialRepository;
import com.example.wecommerce_api.repository.DeviceToken.DeviceTokenRepository;
import com.example.wecommerce_api.repository.Password.PasswordRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import com.example.wecommerce_api.service.Auth.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp extends UserService {
    private final UserRepository userRepository;
    private  final Validation validation;
    private final AuthenticationService authenticationService;
    private final PasswordRepository passwordRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CollectionRepository collectionRepository;
    private final CredencialRepository credencialRepository;
    private final TokenRepository tokenRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    public UserServiceImp(UserRepository userRepository, Validation validation, AuthenticationService authenticationService, PasswordRepository passwordRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CollectionRepository collectionRepository, CredencialRepository credencialRepository, TokenRepository tokenRepository, DeviceTokenRepository deviceTokenRepository) {
        this.userRepository = userRepository;
        this.validation = validation;
        this.authenticationService = authenticationService;
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.collectionRepository = collectionRepository;
        this.credencialRepository = credencialRepository;
        this.tokenRepository = tokenRepository;
        this.deviceTokenRepository = deviceTokenRepository;
    }


    @Override
    public String EditUser(UserInfoRequest request, Integer userId) {
        if(userRepository.findById(userId).isEmpty()){
            throw new NotFoundExceptionHandler("User not found!");
        }
        UserEntity user = userRepository.getById(userId);
            validation.ValidationPhoneNumber(request.getPhoneNumber());
            validation.ValidationInputUserName(request.getUserName());
            user.setName(request.getUserName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setProfilePhoto(request.getPhotoProfile());
            user.setMaplink(request.getMaplink());
            user.setShopAdress(request.getAddress());
            user.setStatus(true);
            userRepository.save(user);
            return "Updated successful!";
    }

    @Override
    public Optional<UserEntity> GetUserInfor(Integer userId) {
        if(userRepository.findById(userId).isEmpty()){
            throw new NotFoundExceptionHandler("User Not Found!");
        }
        return userRepository.findById(userId);
    }

    @Override
    public void deleteUser(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundExceptionHandler("User Not Found!");
        }
        UserEntity user = userOptional.get();
        List<Token> tokens = user.getTokens();
        for (Token token : tokens) {
            tokenRepository.delete(token);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        validation.ValidationEmail(email);
        if(userRepository.findByEmail(email).isEmpty()){
            throw new NotFoundExceptionHandler("User not found!");
        }
        return userRepository.findByEmail(email);
    }


    @Override
    public void VerifyPass(String password, Integer id) throws Exception{
        try {
        UserEntity userEntity = userRepository.getById(id);
        var user = userRepository.findByEmail(userEntity.getEmail()).orElseThrow(() -> new CustomExceptionSecurity(ResponseMessage.INCORRECT_USERNAME));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEntity.getEmail(),
                        password
                )
        );
        } catch (BadCredentialsException e) {
            throw new CustomExceptionSecurity(ResponseMessage.INCORRECT_PASSWORD);
        }
    }

    @Override
    public void ConnectWebill(CredentialEntity credential) {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        if(userRepository.getById(userId) == null){
            throw new NotFoundExceptionHandler("User not found!");
        }
        UserEntity user = userRepository.getById(userId);
        if(credencialRepository.findByUserId(userId) == null){
            CredentialEntity credentialEntity = new CredentialEntity();
            credentialEntity.setClientId(credential.getClientId());
            credentialEntity.setClientSecret(credential.getClientSecret());
            credentialEntity.setUser(user);
            credencialRepository.save(credentialEntity);
        } else {
            CredentialEntity credentialEntity = credencialRepository.findByUserId(userId);
            credentialEntity.setClientId(credential.getClientId());
            credentialEntity.setClientSecret(credential.getClientSecret());
            credentialEntity.setUser(user);
            credencialRepository.save(credentialEntity);
        }
    }

    @Override
    public void DisConnect() {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        if (credencialRepository.findByUserId(userId) == null){
          throw new NotFoundExceptionHandler("cannot deconnect!");
        }
        CredentialEntity credential = credencialRepository.findByUserId(userId);
        credencialRepository.deleteById(credential.getId());
    }

    @Override
    public CredentialEntity getCredentails(Integer userId) {
        if (credencialRepository.findByUserId(userId) == null){
            throw new NotFoundExceptionHandler("Not found!");
        }
        return credencialRepository.findByUserId(userId);
    }

    @Override
    public void InsertDeviceToken(String deviceToken) {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        if(userRepository.findById(userId).isEmpty()){
            throw new NotFoundExceptionHandler("user not found");
        }
        UserEntity user = userRepository.getById(userId);
        DeviceTokenEntity deviceToken1 = new DeviceTokenEntity();
        if(deviceTokenRepository.findByUserId(userId) == null){
            deviceToken1.setDeviceToken(deviceToken);
            deviceToken1.setUser(user);
            deviceTokenRepository.save(deviceToken1);
        }else {
            deviceToken1 = deviceTokenRepository.findByUserId(userId);
            deviceToken1.setDeviceToken(deviceToken);
            deviceToken1.setUser(user);
            deviceTokenRepository.save(deviceToken1);
        }
    }

    @Override
    public DeviceTokenEntity getDeviceToken(Integer userId) {
        if(deviceTokenRepository.findByUserId(userId) == null){
            throw new NotFoundExceptionHandler("Device Token is not found!");
        }
        return deviceTokenRepository.findByUserId(userId);
    }
}
