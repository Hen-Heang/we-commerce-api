package com.example.wecommerce_api.service.Collection;

import com.example.wecommerce_api.entity.CollectionEntity;
import com.example.wecommerce_api.payload.Collection.CollectionResponse;
import com.example.wecommerce_api.payload.Collection.CollectionResponseDetail;

import java.util.List;

public interface CollectionService {
    CollectionEntity CreateCollection(String collectionName, Integer userId) throws Exception;
    void addItem(List<Long> bookMarkId, Long id) throws Exception;
    CollectionEntity Update(Long id, String newCollectionName) throws Exception;
    void deleteCollection(Long id) throws Exception;
    List<CollectionResponse> getAllCollection(Integer userId) throws Exception;
    CollectionResponseDetail getCollectionById(Integer userId, Long id) throws Exception;
    List<CollectionResponse> getCollectionByName(Integer userId, String name) throws Exception;
}
