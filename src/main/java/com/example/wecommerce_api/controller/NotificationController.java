package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.Notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private  final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<?> GetNotification(@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                notificationService.getNotification(userId,pageNumber,pageSize),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeletedNotification(@PathVariable Long id){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        notificationService.deletedNotification(id,userId);
        return ResponseEntity.ok(new ApiResponse<>(
                "Removed successfully!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
}
