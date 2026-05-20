package com.example.wecommerce_api.service.Address;

import com.example.wecommerce_api.exception.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.AddressEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.Address.AddressRequest;
import com.example.wecommerce_api.repository.Address.AddressRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImp extends AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    @Override
    public AddressEntity AddAddressDel(AddressRequest addressRequest) {
        UserEntity auth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHandler("User is not found!"));
        AddressEntity address = new AddressEntity();
        address.setAddress(addressRequest.getAddress());
        address.setDetail(addressRequest.getDetail());
        address.setContact(addressRequest.getContact());
        address.setLabel(addressRequest.getLabel());
        address.setTelephone(addressRequest.getTelephone());
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public List<AddressEntity> getAddressDelivery(Integer userId) {
        return addressRepository.findByUserIdOrderByIdDesc(userId);
    }

    @Override
    public String updateAddress(Long addressId, AddressRequest addressRequest) {
        UserEntity auth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHandler("User not found!"));
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundExceptionHandler("Address not found!"));
        address.setAddress(addressRequest.getAddress());
        address.setDetail(addressRequest.getDetail());
        address.setContact(addressRequest.getContact());
        address.setLabel(addressRequest.getLabel());
        address.setTelephone(addressRequest.getTelephone());
        addressRepository.save(address);
        return "Update successful!";
    }

    @Override
    public AddressEntity getAddressDeliveryById(Integer userid, Long id) {
        AddressEntity address = addressRepository.findByIdAndUserId(id, userid);
        if (address == null) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        return address;
    }
}