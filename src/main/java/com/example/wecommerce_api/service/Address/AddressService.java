package com.example.wecommerce_api.service.Address;

import com.example.wecommerce_api.entity.AddressEntity;
import com.example.wecommerce_api.payload.Address.AddressRequest;

import java.util.List;

public abstract class AddressService {
    public abstract AddressEntity AddAddressDel(AddressRequest request);
    public abstract List<AddressEntity> getAddressDelivery(Integer userId);
    public abstract String updateAddress(Long addressId, AddressRequest request);
    public abstract AddressEntity getAddressDeliveryById(Integer userId, Long id);
}