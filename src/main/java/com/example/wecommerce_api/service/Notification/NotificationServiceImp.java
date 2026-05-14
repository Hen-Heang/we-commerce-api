package com.example.wecommerce_api.service.Notification;

import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.NotificationEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.exception.FieldEmptyExceptionHandler;
import com.example.wecommerce_api.payload.Notification.NotificationResponse;
import com.example.wecommerce_api.repository.Notification.NotificationRepository;
import com.example.wecommerce_api.repository.Product.ProductRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class NotificationServiceImp implements NotificationService{
    @Autowired
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    @Override
    public List<NotificationResponse> getNotification(Integer receiverId,Integer pageNumber,Integer pageSize) {
        UserEntity userReceiver = new UserEntity();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if (userRepository.findById(receiverId).isEmpty()){
            throw new FieldEmptyExceptionHandler("Receiver is not found!");
        }else{
            userReceiver = userRepository.getById(receiverId);
        }
        if(notificationRepository.findByReceiverOrderByCreatedDateDesc(userReceiver,pageRequest).isEmpty()){
            throw new NotFoundExceptionHandler("No record");
        }
        List<NotificationEntity> notifications = notificationRepository.findByReceiverOrderByCreatedDateDesc(userReceiver,pageRequest);
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (NotificationEntity notification:notifications) {
            NotificationResponse notificationResponse = new NotificationResponse();
            notificationResponse.setContant(notification.getContanct());
            notificationResponse.setDecription(notification.getDescription());
            notificationResponse.setCreatedDate(notification.getCreatedDate());
            notificationResponse.setReceiverPhoto(notification.getReceiver().getProfilePhoto());
            notificationResponse.setId(notification.getId());
            notificationResponse.setReceiptId(notification.getReciept().getId());
            notificationResponses.add(notificationResponse);
        }
        return notificationResponses;
    }
    @Override
    public void deletedNotification(Long id,Integer userId) {
        if(notificationRepository.findById(id).isEmpty()||notificationRepository.findByReceiverIdAndId(userId,id) == null){
            throw new FieldEmptyExceptionHandler("Allow deleted only own user!");
        }
        notificationRepository.deleteById(id);
    }

}
