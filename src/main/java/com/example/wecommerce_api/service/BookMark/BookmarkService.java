package com.example.wecommerce_api.service.BookMark;

import com.example.wecommerce_api.payload.Product.ProductResponse;

import java.util.List;

public abstract class BookmarkService {
    public abstract void savedProductToBookmark(Integer userId, Long productId);
    public abstract void deletedBookmarkById(Long productId);
    public abstract List<ProductResponse> getAllBookMarkByTitle(String title, Integer userId);
}
