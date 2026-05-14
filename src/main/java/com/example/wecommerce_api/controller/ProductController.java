package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.Product.ProductRequest;
import com.example.wecommerce_api.payload.TotalItem.TotalItem;
import com.example.wecommerce_api.repository.Product.ProductRepository;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.response.ProductResponse;
import com.example.wecommerce_api.service.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/item")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllItem(@RequestParam int pageNumber,@RequestParam int pageSize){
        return ResponseEntity.ok(new ApiResponse<>(
                productService.getAllPost(pageNumber,pageSize),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(new ApiResponse<>(
                productService.getPostById(id),
                "OK",
                200,
                false,
                LocalDateTime.now()

        ));
    }
    @GetMapping("categoryName/{categoryName}")
    public ResponseEntity<?> getProductByCategoryName(@PathVariable String categoryName,@RequestParam Integer pageNumer,@RequestParam Integer pageSize) {
        return ResponseEntity.ok(new ApiResponse<>(
                productService.getPostByCategoryName(categoryName,pageNumer,pageSize),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

        @GetMapping("title/{title}")
         public ResponseEntity<?> getProductByTitle(@PathVariable String title,@RequestParam Integer pageNumer,@RequestParam Integer pageSize){
            return ResponseEntity.ok(new ApiResponse<>(
                    productService.getPostByTitle(title,pageNumer,pageSize),
                    "OK",
                    200,
                    false,
                    LocalDateTime.now()

            ));
        }

        @PostMapping("/postProduct")
      public ResponseEntity<?> PostProduct(@RequestBody ProductRequest productRequest, @RequestParam String categoryName){
        return ResponseEntity.ok(new ApiResponse<>(
                productService.PostProduct(productRequest,categoryName),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
        }
    @GetMapping("/popular")
    public ResponseEntity<?> getTopProduct(){

        return ResponseEntity.ok(new ApiResponse<>(
                productService.getTopItems(),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @GetMapping("/allItemByCurrenctUser")
    public ResponseEntity<?> getAllItemByUserId(@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        Integer totalPro = productService.getSize("currentUser",id);
        return ResponseEntity.ok(new ProductResponse<>(
                200,
                productService.getAllItemByUserId(id,pageNumber,pageSize),
                totalPro,
                LocalDateTime.now(),
                true
        ));
    }
    @GetMapping("/allItemSelling")
    public ResponseEntity<?> getAllItemSelling(@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        Integer totalPro = productService.getSize("selling",id);
        return ResponseEntity.ok(new ProductResponse<>(
                200,
                productService.getAllItemSelling(id,pageNumber,pageSize),
                totalPro,
                LocalDateTime.now(),
                true
        ));
    }

    @GetMapping("/allItemPurchased")
    public ResponseEntity<?> getAllItemPurchased(@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        Integer totalPro = productService.getSize("purchase",id);

        return ResponseEntity.ok(new ProductResponse<>(
                200,
                productService.getAllItemPurchased(id,pageNumber,pageSize),
                totalPro,
                LocalDateTime.now(),
                true
        ));
    }
    @GetMapping("/allItemSoldOut")
    public ResponseEntity<?> getAllItemSoldOut(@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        Integer totalPro = productService.getSize("souldOut",id);
        return ResponseEntity.ok(new ProductResponse<>(
                200,
                productService.getAllItemSoldOut(id,pageNumber,pageSize),
                totalPro,
                LocalDateTime.now(),
                true
        ));
    }
  @PutMapping("hideProduct/{id}")
    public ResponseEntity<?> HideProduct(@PathVariable("id") Long productId){
      productService.HideProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(
                "Hided successfully!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
  }
  @GetMapping("/totalItem")
  public ResponseEntity<?> getTotalItem(){
      UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Integer id = auth.getId();
      Integer totalItemSoldOut = productService.getSize("souldOut",id);
      Integer totalItemSelling = productService.getSize("selling",id);
      Integer totalItemPurchased = productService.getSize("purchase",id);
      Integer totalAllItem = productService.getSize("currentUser",id);
      TotalItem totalItem = new TotalItem(totalItemSelling,totalItemPurchased,totalItemSoldOut,totalAllItem);
      return ResponseEntity.ok(new ApiResponse<>(
              totalItem,
              "OK",
              200,
              false,
              LocalDateTime.now()
      ));
  }

}
