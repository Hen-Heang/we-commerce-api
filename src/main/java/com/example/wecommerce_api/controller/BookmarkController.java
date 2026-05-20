package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.BookMark.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;


    //save bookmark
    @PostMapping("/saved")
    public ResponseEntity<?> saveProductToBookmark(
            @RequestParam("productId") Long productId
    ){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        bookmarkService.savedProductToBookmark(id, productId);
        return ResponseEntity.ok().body(new ApiResponse<>(
                "Item saved!",
                "OK",
                200,
                false,
                LocalDateTime.now()

        ));
    }

    //delete bookmark
    @DeleteMapping("/unsaved")
    public ResponseEntity<?> deletedBookmarkById(@RequestParam Long productId) {
        bookmarkService.deletedBookmarkById(productId);
        return ResponseEntity.ok().body(new ApiResponse<>(
                "Item unsaved!",
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @GetMapping("/allSaved/{title}")
    public ResponseEntity<?> getAllSavedByTitle(@PathVariable String title){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                bookmarkService.getAllBookMarkByTitle(title,id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
}
