package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.CredentialEntity;
import com.example.wecommerce_api.entity.DeviceTokenEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.UserInfo.UserInfoRequest;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("edit")
    public ResponseEntity<?> EditUser(@RequestBody UserInfoRequest request){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                userService.EditUser(request,id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @GetMapping("userprofile")
    public ResponseEntity<?> getUserInfor(){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                userService.GetUserInfor(id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("userByEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(new ApiResponse<>(
                userService.getUserByEmail(email),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Deleted successful!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @PostMapping("/connectWebill")
    public ResponseEntity<?> ConnectWebill(@RequestBody CredentialEntity credential){
        userService.ConnectWebill(credential);
        return ResponseEntity.ok(new ApiResponse<>(
                "Connected Successfully!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @DeleteMapping("/disConnectWebill")
    public ResponseEntity<?> DisConnectWebill(){
        userService.DisConnect();
        return ResponseEntity.ok(new ApiResponse<>(
                "DisConnected!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("/credentails/{userId}")
    public ResponseEntity<?> GetCredentail(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(new ApiResponse<>(
                userService.getCredentails(userId),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }


    @PostMapping("/deviceToken")
    public ResponseEntity<?> InsertDeviceToken(@RequestParam String deviceToken){
        userService.InsertDeviceToken(deviceToken);
        return ResponseEntity.ok(new ApiResponse<>(
                "successfully!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("deviceToken/{userId}")
    public ResponseEntity<?> getDeviceToken(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(new ApiResponse<>(
                userService.getDeviceToken(userId),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("/verifyPass/{password}")
    public ResponseEntity<?> VerifyPass(@PathVariable("password") String password) throws Exception {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        userService.VerifyPass(password,id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Correct",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
}
