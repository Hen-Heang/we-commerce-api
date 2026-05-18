package com.example.wecommerce_api.service.Purchase;

import com.example.wecommerce_api.entity.*;
import com.example.wecommerce_api.exception.FieldEmptyExceptionHandler;
import com.example.wecommerce_api.exception.exceptionValidateInput.Validation;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import com.example.wecommerce_api.payload.Purchase.PurchaseRequest;
import com.example.wecommerce_api.payload.Receipt.ReceiptResponse;
import com.example.wecommerce_api.repository.Address.AddressRepository;
import com.example.wecommerce_api.repository.Notification.NotificationRepository;
import com.example.wecommerce_api.repository.Product.ProductRepository;
import com.example.wecommerce_api.repository.Purchase.PurchaseRpository;
import com.example.wecommerce_api.repository.Receipt.ReceiptRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import com.example.wecommerce_api.service.Notification.NotificationServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImp implements PurchaseService{
    private final PurchaseRpository purchaseRpository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final ReceiptRepository receiptRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationServiceImp notificationServiceImp;
    private final Validation validation;



    @Override
    public ReceiptResponse addPurchase(PurchaseRequest purchaseRequest) {
        UserEntity payer=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = payer.getId();
        PurchaseDetailEntity purchaseDetail = new PurchaseDetailEntity();

        if(userRepository.findById(id).isEmpty()){
            throw new FieldEmptyExceptionHandler("User not found!");
        }
        if (addressRepository.findById(purchaseRequest.getAddressDeliveryId()).isEmpty()){
            throw new FieldEmptyExceptionHandler("Address not found!");
        }
        if (productRepository.findById(purchaseRequest.getProductId()).isEmpty()){
            throw new FieldEmptyExceptionHandler("Product not found!");
        }
        ProductEntity product = productRepository.getById(purchaseRequest.getProductId());
        if(id == product.getUser().getId()){
            throw new FieldEmptyExceptionHandler("Cannot order your own product!");
        }
        AddressEntity address = addressRepository.getById(purchaseRequest.getAddressDeliveryId());
        UserEntity seller = userRepository.getById(product.getUser().getId());
        purchaseDetail.setUser(payer);
        purchaseDetail.setProduct(product);
        purchaseDetail.setAddress(address);
        purchaseDetail.setPaymentMethod(purchaseRequest.getPaymentMethod());
        purchaseDetail.setRemark(purchaseRequest.getRemark());
        purchaseDetail.setCreatedDate(LocalDateTime.now());
        purchaseDetail = purchaseRpository.save(purchaseDetail);
        RecieptEntity reciept = new RecieptEntity();
        reciept.setPurchase(purchaseDetail);
        reciept.setReference(purchaseRequest.getRefernce());
        reciept.setPaidBy(purchaseRequest.getPaidBy());
        reciept.setPaidDate(LocalDateTime.now());
        reciept = receiptRepository.save(reciept);
        NotificationEntity notification = new NotificationEntity();
        notification.setReciept(reciept);
        notification.setReceiver(seller);
        notification.setSender(payer);
        notification.setIsRead(false);
        notification.setContanct("You receive an order");
        notification.setDescription("$"+product.getTotalAmount()+" from "+payer.getName()+" ");
        notification.setCreatedDate(LocalDateTime.now());
        notificationRepository.save(notification);
        NotificationEntity notification1 = new NotificationEntity();
        notification1.setReciept(reciept);
        notification1.setReceiver(payer);
        notification1.setSender(payer);
        notification1.setIsRead(false);
        notification1.setContanct("You placed an order");
        notification1.setDescription("$"+product.getTotalAmount()+" to "+seller.getName()+" ");
        notification1.setCreatedDate(LocalDateTime.now());
        notificationRepository.save(notification1);
        product.setStatus("Purchased");
        productRepository.save(product);
        ProductResponse productResponse = new ProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getStatus(),false,product.getCreatedDate(), product.getTotalAmount(),product.getPhoto());
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setProductResponse(productResponse);
        receiptResponse.setPayer(payer.getName());
        receiptResponse.setSeller(seller.getName());
        receiptResponse.setReference(reciept.getReference());
        receiptResponse.setOrderDate(reciept.getPaidDate());
        receiptResponse.setPaidBy(reciept.getPaidBy());
        return receiptResponse;
    }
}
