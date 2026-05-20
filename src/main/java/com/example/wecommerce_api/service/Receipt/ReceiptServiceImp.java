package com.example.wecommerce_api.service.Receipt;

import com.example.wecommerce_api.exception.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.ProductEntity;
import com.example.wecommerce_api.entity.ReceiptEntity;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import com.example.wecommerce_api.payload.Receipt.ReceiptResponse;
import com.example.wecommerce_api.repository.Receipt.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ReceiptServiceImp implements ReceiptService{
    private final ReceiptRepository receiptRepository;


    @Override
    public ReceiptResponse getReceiptById(Long receiptId) {
        if(receiptRepository.findById(receiptId).isEmpty()){
            throw new NotFoundExceptionHandler("No record");
        }

        ReceiptEntity receipt = receiptRepository.getById(receiptId);
        ProductEntity product = receipt.getPurchase().getProduct();
        ProductResponse productResponse = new ProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getStatus(),false,product.getCreatedDate(), product.getTotalAmount(),product.getPhoto());
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setProductResponse(productResponse);
        receiptResponse.setPayer(receipt.getPurchase().getUser().getName());
        receiptResponse.setSeller(receipt.getPurchase().getProduct().getUser().getName());
        receiptResponse.setReference(receipt.getReference());
        receiptResponse.setOrderDate(receipt.getPaidDate());
        receiptResponse.setPaidBy(receipt.getPaidBy());
        return receiptResponse;
    }
}
