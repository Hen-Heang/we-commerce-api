package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.Collection.CollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
    @PostMapping("addCollection")
    public ResponseEntity<?> CreateCollection(@RequestParam String collectionName) throws Exception{
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                collectionService.CreateCollection(collectionName,userId),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @PutMapping("addBookMark/{id}")
    public ResponseEntity<?> AddItem(@RequestParam List<Long> bookMarkId,@PathVariable("id") Long id) throws Exception{
        collectionService.addItem(bookMarkId,id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Add successful!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateCollection(@PathVariable("id") Long id,@RequestParam String newCollectionName) throws Exception{
        return ResponseEntity.ok(new ApiResponse<>(
                collectionService.Update(id,newCollectionName),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable("id") Long id)throws Exception{
        collectionService.deleteCollection(id);
        return ResponseEntity.ok(new ApiResponse<>(
                "Deleted successfully!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }

    @GetMapping("allCollection")
    public ResponseEntity<?> GetAllCollection()throws Exception{
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                collectionService.getAllCollection(userId),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> GetCollectionById(@PathVariable("id") Long id)throws Exception{
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                collectionService.getCollectionById(userId,id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("/collectionName/{name}")
    public ResponseEntity<?> GetCollectionByName(@PathVariable("name") String name)throws Exception{
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                collectionService.getCollectionByName(userId,name),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }


}
