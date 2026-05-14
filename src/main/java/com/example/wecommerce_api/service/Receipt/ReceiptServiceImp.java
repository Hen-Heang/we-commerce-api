package com.example.wecommerce_api.service.Receipt;

import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.ProductEntity;
import com.example.wecommerce_api.entity.RecieptEntity;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import com.example.wecommerce_api.payload.Receipt.ReceiptResponse;
import com.example.wecommerce_api.repository.Receipt.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImp implements ReceiptService{
    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImp(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public ReceiptResponse getReceiptById(Long receiptId) {
        if(receiptRepository.findById(receiptId).isEmpty()){
            throw new NotFoundExceptionHandler("No record");
        }

        RecieptEntity reciept = receiptRepository.getById(receiptId);
        ProductEntity product = reciept.getPurchase().getProduct();
        ProductResponse productResponse = new ProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getStatus(),false,product.getCreatedDate(), product.getTotalAmount(),product.getPhoto());
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setProductResponse(productResponse);
        receiptResponse.setPayer(reciept.getPurchase().getUser().getName());
        receiptResponse.setSeller(reciept.getPurchase().getProduct().getUser().getName());
        receiptResponse.setReference(reciept.getReference());
        receiptResponse.setOrderDate(reciept.getPaidDate());
        receiptResponse.setPaidBy(reciept.getPaidBy());
        return receiptResponse;
    }
}
