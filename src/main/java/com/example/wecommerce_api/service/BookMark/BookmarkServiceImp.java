package com.example.wecommerce_api.service.BookMark;

import com.example.wecommerce_api.exception.constand.InternalServerExeptionHandler;
import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.BookMarkEntity;
import com.example.wecommerce_api.entity.CollectionEntity;
import com.example.wecommerce_api.entity.ProductEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import com.example.wecommerce_api.repository.BookMark.BookMarkRepository;
import com.example.wecommerce_api.repository.Collection.CollectionRepository;
import com.example.wecommerce_api.repository.Product.ProductRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import com.example.wecommerce_api.service.Product.ProductServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookmarkServiceImp extends BookmarkService {
    private final BookMarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CollectionRepository collectionRepository;
    private  final ProductServiceImp productServiceImp;

    public BookmarkServiceImp(BookMarkRepository bookmarkRepository, UserRepository userRepository, ProductRepository productRepository, CollectionRepository collectionRepository, ProductServiceImp productServiceImp) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.collectionRepository = collectionRepository;
        this.productServiceImp = productServiceImp;
    }

    @Override
    public void savedProductToBookmark(Integer userId, Long productId) {
        if(userRepository.findById(userId).isEmpty()){
            throw new NotFoundExceptionHandler("User is not found!");
        }
        if (productRepository.findById(productId).isEmpty()){
            throw new NotFoundExceptionHandler("Product is not found!");
        }
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + productId + " not found"));

        CollectionEntity collection = collectionRepository.findByUserIdAndName(userId, "All Post");
        if(collection == null){
            collection = new CollectionEntity();
            collection.setName("All Post");
            collection.setUser(user);
            collection.setCreateDate(LocalDateTime.now());
            collectionRepository.save(collection);
        }
        if(bookmarkRepository.findByProductIdAndUserId(productId,userId) == null) {
            BookMarkEntity bookmarkEntity = new BookMarkEntity();
            bookmarkEntity.setCollection(collection);
            bookmarkEntity.setUser(user);
            bookmarkEntity.setProduct(product);
            bookmarkEntity.setStatus("BOOKMARKED");
            bookmarkEntity.setCreatedDate(LocalDateTime.now());
            // Save the bookmark entity
            bookmarkRepository.save(bookmarkEntity);
        }else {
            throw new NotFoundExceptionHandler("This item save already!");
        }
    }

    @Override
    public void deletedBookmarkById(Long productId) {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        BookMarkEntity bookMark = bookmarkRepository.findByProductIdAndUserId(productId,id);
        if(bookMark == null){
            throw new NotFoundExceptionHandler("Item Save is not found!");
        }
        bookmarkRepository.deleteById(bookMark.getId());
    }

    @Override
    public List<ProductResponse> getAllBookMarkByTitle(String title,Integer userId) {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<BookMarkEntity> bookMarkEntities = bookmarkRepository.findByProduct_TitleContainingIgnoreCaseAndUserId(title,userId);
        if (bookMarkEntities.isEmpty()){
            return productResponses;
        }
        for (BookMarkEntity bookMark : bookMarkEntities){
            productResponses.add(productServiceImp.SetDataToProductResponse(bookMark.getId(),bookMark.getProduct().getTitle()
                    ,bookMark.getProduct().getPrice(),bookMark.getProduct().getDiscountValues(),bookMark.getProduct().getDiscountType(),bookMark.getCreatedDate(),bookMark.getProduct().getPhoto(),bookMark.getProduct().getStatus(),true));
        }
        return productResponses;
    }


}
