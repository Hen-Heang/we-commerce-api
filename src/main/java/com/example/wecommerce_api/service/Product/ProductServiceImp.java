package com.example.wecommerce_api.service.Product;

import com.example.wecommerce_api.exception.constand.FieldBlankExceptionHandler;
import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.*;
import com.example.wecommerce_api.exception.FieldEmptyExceptionHandler;
import com.example.wecommerce_api.exception.exceptionValidateInput.Validation;
import com.example.wecommerce_api.payload.Product.ProductRequest;
import com.example.wecommerce_api.payload.Product.ProductResponse;
import com.example.wecommerce_api.repository.BookMark.BookMarkRepository;
import com.example.wecommerce_api.repository.Category.CategoryRepository;
import com.example.wecommerce_api.repository.Photo.PhotoRepository;
import com.example.wecommerce_api.repository.Product.ProductRepository;
import com.example.wecommerce_api.repository.ProductViewCount.ProductViewCountRepository;
import com.example.wecommerce_api.repository.Purchase.PurchaseRpository;
import com.example.wecommerce_api.repository.User.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final ProductViewCountRepository productViewCountRepository;
    private final Validation validation;
    private final BookMarkRepository bookMarkRepository;
    private final PurchaseRpository purchaseRpository;

    public ProductServiceImp(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, PhotoRepository photoRepository, ProductViewCountRepository productViewCountRepository, Validation validation, BookMarkRepository bookMarkRepository, PurchaseRpository purchaseRpository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.productViewCountRepository = productViewCountRepository;
        this.validation = validation;
        this.bookMarkRepository = bookMarkRepository;
        this.purchaseRpository = purchaseRpository;
    }
    public ProductResponse SetDataToProductResponse(Long id, String title, Double price,Double discountValus, Boolean discountType, LocalDateTime createdDate, List<PhotoEntity> photo,String status,Boolean isSave){
        Double totalAmount = (double) 0;
        if(discountType == true){
            totalAmount = price - (price*(discountValus/100));
        }else {
            totalAmount = price - discountValus;
        }
        ProductResponse productResponse = new ProductResponse(id,title,price,status,isSave,createdDate,totalAmount,photo);
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllPost(int pageNumber,int pageSize) {


        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        List<ProductResponse> productResponses = new ArrayList<>();
        List<ProductEntity> productEntities = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if(productRepository.findAll(pageRequest).isEmpty()){
            throw new FieldEmptyExceptionHandler("No record!");
        }
            productEntities = productRepository.findAllByOrderByCreatedDateDesc(pageRequest);

        for (ProductEntity product : productEntities){
            if(product.getStatus().equals("Selling")) {
                if(product.getIsHide() == false) {
                    if (bookMarkRepository.findByProductIdAndUserId(product.getId(), userId) != null) {
                        productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), true));
                    } else {
                        productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), false));
                    }
                }
            }
        }
        if(productResponses.isEmpty()){
            throw new FieldEmptyExceptionHandler("No record!");
        }
        return productResponses;
    }

    @Override
    public Optional<ProductEntity> getPostById(Long productId) {
        if(productRepository.findById(productId).isEmpty()){
            throw new NotFoundExceptionHandler("No record!");
        }
       Optional<ProductEntity> product = productRepository.findById(productId);
        return product;
    }

    @Override
    public List<ProductResponse> getPostByCategoryName(String categoryName,Integer pageNumber,Integer pageSize) {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if (categoryRepository.getByCategoryName(categoryName)==null){
            throw new NotFoundExceptionHandler("Category not found!");
        }
        if(productRepository.findAllByCategory_CategoryName(categoryName,pageRequest).isEmpty()){
            throw new NotFoundExceptionHandler("No record!");
        }
        List<ProductEntity> productEntities = productRepository.findAllByCategory_CategoryName(categoryName,pageRequest);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductEntity product:productEntities) {
            if (product.getStatus().equals("Selling")) {
                if (product.getIsHide() == false) {
                    if (bookMarkRepository.findByProductIdAndUserId(product.getId(), userId) != null) {
                        productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), true));
                    } else {
                        productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), false));
                    }
                }
            }
        }
        return productResponses;
    }

    @Override
    public List<ProductResponse> getPostByTitle(String title,Integer pageNumber,Integer pageSize) {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        List<ProductResponse> productResponses = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if(productRepository.findByTitleContainingIgnoreCase(title,pageRequest).isEmpty()){
            throw new NotFoundExceptionHandler("Product is not found!");
        }
        List<ProductEntity> productEntities = productRepository.findByTitleContainingIgnoreCase(title,pageRequest);
        for (ProductEntity product : productEntities){
            if (product.getStatus().equals("Selling")){
            if(product.getIsHide() == false) {
                if (bookMarkRepository.findByProductIdAndUserId(product.getId(), userId) != null) {
                    productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), true));
                } else {
                    productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), false));
                }
             }
            }
        }
        return productResponses;
    }

    @Override
    public ProductResponse PostProduct(ProductRequest productRequest, String categoryName) {
        UserEntity auth =(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        if (categoryRepository.getByCategoryName(categoryName) == null){
            throw new NotFoundExceptionHandler("Category not found");
        }
        if(productRequest.getTitle()==null||productRequest.getTitle().equals("")||productRequest.getTitle().equals("string")){
            throw new FieldBlankExceptionHandler("Title cannot blank!");
        }
        if(productRequest.getPrice()==null||productRequest.getPrice()== 0 || productRequest.getPrice()< 0 || productRequest.getPrice() > 100000000000000.0 ){
            throw new FieldBlankExceptionHandler("Price is not correct!");
        }
        // Default missing optional fields so we don't NPE on auto-unbox.
        Double discountValues = productRequest.getDiscountValues() != null ? productRequest.getDiscountValues() : 0.0;
        Boolean discountType = productRequest.getDiscountType() != null ? productRequest.getDiscountType() : Boolean.FALSE;
        if (discountValues < 0 || discountValues >= 100.0){
            throw new FieldBlankExceptionHandler("Discount is not correct!");
        }

        if (productRequest.getPhoto() == null || productRequest.getPhoto().isEmpty()){
            throw new FieldBlankExceptionHandler("photo cannot blank!");
        }
        CategoryEntity category = categoryRepository.getByCategoryName(categoryName);
        if(userRepository.findById(id).isEmpty()){
            throw new NotFoundExceptionHandler("User not found");
        }
        Double totalAmount;
        if (Boolean.TRUE.equals(discountType)){
            totalAmount = productRequest.getPrice() - (productRequest.getPrice() * (discountValues / 100));
        } else {
            totalAmount = productRequest.getPrice() - discountValues;
        }
        UserEntity user = userRepository.getById(id);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setUser(user);
        productEntity.setCategory(category);
        productEntity.setTitle(productRequest.getTitle());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setStatus("Selling");
        productEntity.setDiscountValues(discountValues);
        productEntity.setDiscountType(discountType);
        productEntity.setCodition(productRequest.getCodition());
        productEntity.setBrand(productRequest.getBrand());
        productEntity.setModel(productRequest.getModel());
        productEntity.setColor(productRequest.getColor());
        productEntity.setYear(productRequest.getYear());
        productEntity.setSize(productRequest.getSize());
        productEntity.setType(productRequest.getType());
//        productEntity.setPhoto(productRequest.getPhoto());
        productEntity.setIsHide(false);
        productEntity.setCreatedDate(LocalDateTime.now());
        productEntity.setTotalAmount(totalAmount);
        productEntity = productRepository.save(productEntity);
        for (PhotoEntity photo : productRequest.getPhoto()) {
            PhotoEntity photo1 = new PhotoEntity();
            photo1.setPhoto(photo.getPhoto());
            photo1.setProduct(productEntity);
            photoRepository.save(photo1);
        }
        productEntity = productRepository.save(productEntity);
        ProductResponse productResponse = new ProductResponse(productEntity.getId(), productEntity.getTitle(), productEntity.getPrice(), productEntity.getStatus(), false,productEntity.getCreatedDate(), productEntity.getTotalAmount(), productEntity.getPhoto());
        return productResponse;
    }

    @Override
    public List<ProductResponse> getTopItems() {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        if(productViewCountRepository.findTop10ByProductStatusOrderByCountDesc("Selling").isEmpty()){
            throw new NotFoundExceptionHandler("No Item!");
        }
        List<ProductViewCountEntity> productViewCount = productViewCountRepository.findTop10ByProductStatusOrderByCountDesc("Selling");
        List<ProductEntity> products = new ArrayList<>();
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductViewCountEntity productViewCount1 : productViewCount) {
            ProductEntity product = productRepository.getById(productViewCount1.getProduct().getId());
                products.add(product);
        }
        if (products.isEmpty()){
            throw new NotFoundExceptionHandler("No record!");
        }
        for (ProductEntity product : products){
                if (product.getIsHide() == false) {
                    if (bookMarkRepository.findByProductIdAndUserId(product.getId(), userId) != null) {
                        productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), true));
                    } else {
                        productResponses.add(SetDataToProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getDiscountValues(), product.getDiscountType(), product.getCreatedDate(), product.getPhoto(), product.getStatus(), false));
                    }
            }
        }
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllItemByUserId(Integer userId,Integer pageNumber,Integer pageSize) {
        List<ProductEntity> productEntities = new ArrayList<>();
        List<ProductResponse> productResponses = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
//       if (!(productRepository.findAllByUser_Id(userId,pageRequest).isEmpty())) {
        if(productRepository.findAllByUser_Id(userId, pageRequest).isEmpty() && purchaseRpository.findByUserIdOrderByCreatedDateDesc(userId,pageRequest).isEmpty()){
            throw new NotFoundExceptionHandler("No record");
        }
           productEntities = productRepository.findAllByUser_Id(userId, pageRequest);
           for (ProductEntity product : productEntities) {
                   ProductResponse productResponse = new ProductResponse();
                   if (product.getStatus().equals("Purchased")){
                       productResponse.setId(product.getId());
                       productResponse.setTitle(product.getTitle());
                       productResponse.setPrice(product.getPrice());
                       productResponse.setStatus("Sold out");
                       productResponse.setIsSaved(false);
                       productResponse.setCreatedDate(product.getCreatedDate());
                       productResponse.setTotalAmount(product.getTotalAmount());
                       productResponse.setPhoto(product.getPhoto());
                       productResponses.add(productResponse);
                   } else {
                   productResponse.setId(product.getId());
                   productResponse.setTitle(product.getTitle());
                   productResponse.setPrice(product.getPrice());
                   productResponse.setStatus(product.getStatus());
                   productResponse.setIsSaved(false);
                   productResponse.setCreatedDate(product.getCreatedDate());
                   productResponse.setTotalAmount(product.getTotalAmount());
                   productResponse.setPhoto(product.getPhoto());
                   productResponses.add(productResponse);
               }

           }
         List<PurchaseDetailEntity>  purchaseDetailEntities = purchaseRpository.findByUserIdOrderByCreatedDateDesc(userId,pageRequest);
           for (PurchaseDetailEntity product : purchaseDetailEntities) {
               ProductResponse productResponse = new ProductResponse();
               if (product.getProduct().getStatus().equals("Purchased")){
                   productResponse.setId(product.getProduct().getId());
                   productResponse.setTitle(product.getProduct().getTitle());
                   productResponse.setPrice(product.getProduct().getPrice());
                   productResponse.setStatus(product.getProduct().getStatus());
                   productResponse.setIsSaved(false);
                   productResponse.setCreatedDate(product.getCreatedDate());
                   productResponse.setTotalAmount(product.getProduct().getTotalAmount());
                   productResponse.setPhoto(product.getProduct().getPhoto());
                   productResponses.add(productResponse);
               }

           }

//       }
        return  productResponses;
    }

    @Override
    public List<ProductResponse> getAllItemSelling(Integer userId,Integer pageNumber,Integer pageSize) {
        List<ProductEntity> productEntities = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if(productRepository.findAllByStatusAndUser_IdOrderByCreatedDateDesc("Selling",userId,pageRequest).isEmpty()){
            throw new FieldEmptyExceptionHandler("No record");
        }
        productEntities = productRepository.findAllByStatusAndUser_IdOrderByCreatedDateDesc("Selling",userId,pageRequest);
       List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductEntity product:productEntities) {
            ProductResponse productResponse = new ProductResponse(product.getId(), product.getTitle(), product.getPrice(), product.getStatus(), false,product.getCreatedDate(), product.getTotalAmount(),product.getPhoto());
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllItemPurchased(Integer userId,Integer pageNumber,Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if(purchaseRpository.findAll().isEmpty()){
            throw new FieldEmptyExceptionHandler("No record");
        }
        List<PurchaseDetailEntity> purchaseDetail = purchaseRpository.findByUserIdOrderByCreatedDateDesc(userId,pageRequest);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (PurchaseDetailEntity purchase:purchaseDetail) {
            ProductResponse productResponse = new ProductResponse();
            if (purchase.getProduct().getStatus().equals("Purchased")) {
                productResponse = new ProductResponse(
                        purchase.getProduct().getId(),
                        purchase.getProduct().getTitle(),
                        purchase.getProduct().getPrice(),
                        purchase.getProduct().getStatus(),
                        false,
                        purchase.getCreatedDate(),
                        purchase.getProduct().getTotalAmount(),
                        purchase.getProduct().getPhoto()
                );

                productResponses.add(productResponse);
            }
        }
        return productResponses;
    }
    @Override
    public List<ProductResponse> getAllItemSoldOut(Integer userId,Integer pageNumber,Integer pageSize) {
        List<ProductEntity> productEntities = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        if(productRepository.findAllByStatusAndUser_IdOrderByCreatedDateDesc("Sold out",userId,pageRequest).isEmpty() && productRepository.findAllByStatusAndUser_IdOrderByCreatedDateDesc("Purchased",userId,pageRequest).isEmpty() ){
            throw new FieldEmptyExceptionHandler("No record");
        }
        productEntities = productRepository.findAllByStatusAndUser_IdOrderByCreatedDateDesc("Sold out",userId,pageRequest);
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductEntity product:productEntities) {
            ProductResponse productResponse = new ProductResponse(
                    product.getId(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getStatus(),
                    false,
                    product.getCreatedDate(),
                    product.getTotalAmount(),
                    product.getPhoto()
            );
            productResponses.add(productResponse);
        }
        productEntities = productRepository.findAllByStatusAndUser_IdOrderByCreatedDateDesc("Purchased",userId,pageRequest);
        for (ProductEntity product:productEntities) {
            ProductResponse productResponse = new ProductResponse(
                    product.getId(),
                    product.getTitle(),
                    product.getPrice(),
                    "Sold out",
                    false,
                    product.getCreatedDate(),
                    product.getTotalAmount(),
                    product.getPhoto()
            );
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    @Override
    public void UpdateStatus(Long productId) {
        if(productRepository.findById(productId).isEmpty()){
            throw new NotFoundExceptionHandler("Product not found!");
        }
        ProductEntity product = productRepository.getById(productId);
        if(product.getStatus().equals("Purchased")){
            product.setCreatedDate(LocalDateTime.now());
            product.setStatus("Sold out");
            productRepository.save(product);
        }else {
            throw new NotFoundExceptionHandler("Cannot update this product!");
        }

    }
    @Override
    public void HideProduct(Long productId) {
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        if(productRepository.findById(productId).isEmpty()){
            throw new NotFoundExceptionHandler("Product not found!");
        }
        ProductEntity product = productRepository.getById(productId);
        if(product.getUser().getId() == userId){
            product.setIsHide(true);
            productRepository.save(product);
        }else{
            throw new NotFoundExceptionHandler("Product id "+product.getId()+" cannot hide");
        }
    }
    @Override
    public Integer getSize(String condition,Integer userId) {
        int size = 0;
        if(condition.equals("currentUser")){
            size = productRepository.findAllByUser_Id(userId).size();
            List<PurchaseDetailEntity> purchaseDetailEntities = purchaseRpository.findAllByUserId(userId);
            for (PurchaseDetailEntity purchaseDetail:purchaseDetailEntities) {
                if (purchaseDetail.getProduct().getStatus().equals("Purchased")) {
                    size +=1 ;
                }
            }
            return size;
        }else if(condition.equals("selling")){
            return productRepository.findAllByStatusAndUser_Id("Selling",userId).size();
        }else if (condition.equals("purchase")){
            List<PurchaseDetailEntity> purchaseDetailEntities = purchaseRpository.findAllByUserId(userId);
            for (PurchaseDetailEntity purchaseDetail:purchaseDetailEntities) {
                if (purchaseDetail.getProduct().getStatus().equals("Purchased")) {
                    size +=1 ;
                }
            }
            return size;
        } else if (condition.equals("souldOut")) {
            size = productRepository.findAllByStatusAndUser_Id("Sold out",userId).size()+productRepository.findAllByStatusAndUser_Id("Purchased",userId).size();
           return size;
        }else{
            return 0;
        }
    }
}
