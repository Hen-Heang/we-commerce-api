package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.BookMarkEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.BookMark.BookmarkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

    @Autowired
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    //save bookmark
    @PostMapping("/saved")
    public ResponseEntity<?> saveProductToBookmark(
            @RequestParam("productId") Long productId
    ){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        bookmarkService.savedProductToBookmark(id, productId);
        return ResponseEntity.ok().body(new ApiResponse<String>(
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
        return ResponseEntity.ok().body(new ApiResponse<String>(
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
