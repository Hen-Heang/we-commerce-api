package com.example.wecommerce_api.service.Collection;

import com.example.wecommerce_api.exception.constand.FieldBlankExceptionHandler;
import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.*;
import com.example.wecommerce_api.enums.ResponseMessage;
import com.example.wecommerce_api.exception.BadRequestException;
import com.example.wecommerce_api.exception.CustomExceptionSecurity;
import com.example.wecommerce_api.exception.FieldEmptyExceptionHandler;
import com.example.wecommerce_api.payload.BookMark.BookMarkResponse;
import com.example.wecommerce_api.payload.Collection.CollectionResponse;
import com.example.wecommerce_api.payload.Collection.CollectionResponseDetail;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import com.example.wecommerce_api.repository.BookMark.BookMarkRepository;
import com.example.wecommerce_api.repository.Collection.CollectionRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import com.example.wecommerce_api.service.Product.ProductServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImp implements CollectionService{
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final ProductServiceImp productServiceImp;

    public CollectionServiceImp(CollectionRepository collectionRepository, UserRepository userRepository, BookMarkRepository bookMarkRepository, ProductServiceImp productServiceImp) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.productServiceImp = productServiceImp;
    }


    @Override
    public CollectionEntity CreateCollection(String collectionName,Integer userId) throws Exception{
        try {
            if (userRepository.getById(userId) == null) {
                throw new FieldEmptyExceptionHandler("User not found!");
            }
            if(collectionRepository.findByUserIdAndName(userId,collectionName) == null) {
                CollectionEntity collection = new CollectionEntity();
                UserEntity user = userRepository.getById(userId);
                collection.setUser(user);
                collection.setCreateDate(LocalDateTime.now());
                collection.setName(collectionName);
                return collectionRepository.save(collection);
            }else{
                throw new FieldBlankExceptionHandler("Collection name cannot duplicate");
            }
        }catch (BadRequestException e){
            throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
        }
    }

    @Override
    public void addItem(List<Long> bookMarkId,Long id)throws Exception {
        try {
            if(collectionRepository.findById(id).isEmpty()){
                throw new NotFoundExceptionHandler("Collection is not found!");
            }
            CollectionEntity collection = collectionRepository.getById(id);
            for (Long bookmarkId: bookMarkId) {
                if(bookMarkRepository.findById(bookmarkId).isEmpty()){
                    throw new NotFoundExceptionHandler("Item saved is not found!");
                }
                BookMarkEntity bookMarkEntity = bookMarkRepository.getById(bookmarkId);
                bookMarkEntity.setCollection(collection);
                bookMarkRepository.save(bookMarkEntity);
            }
        }catch (BadRequestException e){
            throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
        }

    }

    @Override
    public CollectionEntity Update(Long id,String newCollectionName) throws Exception{
        try {
            if(collectionRepository.findById(id).isEmpty()){
                throw new FieldEmptyExceptionHandler("Collection is not found!");
            }
            CollectionEntity collection = collectionRepository.getById(id);
            collection.setName(newCollectionName);
            return collectionRepository.save(collection);
        }catch (BadRequestException e) {
            throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
        }
    }

    @Override
    public void DateteCollection(Long id) throws Exception{
       try {
           if (id == 1) {
               throw new FieldEmptyExceptionHandler("This Collection cannot Deleted!");
           } else if (collectionRepository.findById(id).isEmpty()) {
               throw new FieldEmptyExceptionHandler("Collection is not found!");
           }
           collectionRepository.deleteById(id);
       }catch (BadRequestException e){
           throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
       }
    }
    @Override
    public List<CollectionResponse> getAllCollection(Integer userId)throws Exception {
        try{
        List<CollectionResponse> collectionResponses = new ArrayList<>();
        List<CollectionEntity> collectionEntities = collectionRepository.findByUserIdOrderByCreateDateAsc(userId);
        for (CollectionEntity collection : collectionEntities) {
            List<String> photoResponses = new ArrayList<>();
            List<BookMarkEntity> bookMarks = collection.getBookMark();
            for (BookMarkEntity bookMark:bookMarks) {
                ProductEntity product = bookMark.getProduct();
                List<PhotoEntity> photos = product.getPhoto();
                for (PhotoEntity photo:photos) {
                    String photoResponse = photo.getPhoto();
                    photoResponses.add(photoResponse);
                }
            }
            CollectionResponse collectionResponse = new CollectionResponse();
            collectionResponse.setId(collection.getId());
            collectionResponse.setName(collection.getName());
            collectionResponse.setCreatedDate(LocalDateTime.now());
            collectionResponse.setPhoto(photoResponses);
            collectionResponses.add(collectionResponse);
        }
        return collectionResponses;
        }catch (BadRequestException e){
            throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
        }
    }

    @Override
    public CollectionResponseDetail getCollectionById(Integer userId, Long id) throws Exception{
       try {
        CollectionResponseDetail collectionResponse = new CollectionResponseDetail();
        if (collectionRepository.findByIdAndUserId(id,userId)==null){
            throw new NotFoundExceptionHandler("Collection is not found!");
        }
        CollectionEntity collection = collectionRepository.findByIdAndUserId(id,userId);
        List<BookMarkEntity> bookMarks = collection.getBookMark();
        List<BookMarkResponse> bookMarkResponses = new ArrayList<>();
        for (BookMarkEntity bookMark:bookMarks) {
            BookMarkResponse bookMarkResponse = new BookMarkResponse();
            bookMarkResponse.setId(bookMark.getId());
            bookMarkResponse.setStatus(bookMark.getStatus());
            bookMarkResponse.setCreatedDate(bookMark.getCreatedDate());
            ProductEntity product = bookMark.getProduct();
            ProductResponse productResponse = productServiceImp.SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(),true);
            bookMarkResponse.setProduct(productResponse);
            bookMarkResponses.add(bookMarkResponse);
        }
        collectionResponse.setId(collection.getId());
        collectionResponse.setName(collection.getName());
        collectionResponse.setCreatedDate(LocalDateTime.now());
        collectionResponse.setBookMark(bookMarkResponses);
        return collectionResponse;
       }catch (BadRequestException e){
           throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
       }
    }

    @Override
    public List<CollectionResponse> getCollectionByName(Integer userId, String name)throws Exception {
       try {
        if(collectionRepository.findByNameContainingIgnoreCaseAndUserId(name,userId) == null){
            throw new NotFoundExceptionHandler("Collection is not found!");
        }
        List<CollectionEntity> collectionEntities = collectionRepository.findByNameContainingIgnoreCaseAndUserId(name,userId);
            List<String> photoResponses = new ArrayList<>();
           List<BookMarkEntity> bookMarks = new ArrayList<>();
           List<CollectionResponse> collectionResponses = new ArrayList<>();
           for (CollectionEntity collectionEntitie:collectionEntities) {
                bookMarks= collectionEntitie.getBookMark();
               for (BookMarkEntity bookMark:bookMarks) {
                   ProductEntity product = bookMark.getProduct();
                   List<PhotoEntity> photos = product.getPhoto();
                   for (PhotoEntity photo:photos) {
                       String photoResponse = photo.getPhoto();
                       photoResponses.add(photoResponse);
                   }
               }
               CollectionResponse collectionResponse = new CollectionResponse();
               collectionResponse.setId(collectionEntitie.getId());
               collectionResponse.setName(collectionEntitie.getName());
               collectionResponse.setCreatedDate(LocalDateTime.now());
               collectionResponse.setPhoto(photoResponses);
               collectionResponses.add(collectionResponse);
            }
           if (collectionResponses.isEmpty()){
               throw new NotFoundExceptionHandler("No record!");
           }
        return collectionResponses;
       }catch (BadRequestException e){
           throw new CustomExceptionSecurity(ResponseMessage.UNAUTHORIZED);
       }
    }
}
