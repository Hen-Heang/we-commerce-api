package com.example.wecommerce_api.service.Address;

import com.example.wecommerce_api.exception.constand.NotFoundExceptionHandler;
import com.example.wecommerce_api.entity.AddressEntity;
import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.exception.FieldEmptyExceptionHandler;
import com.example.wecommerce_api.exception.exceptionValidateInput.Validation;
import com.example.wecommerce_api.payload.Address.AddressRequest;
import com.example.wecommerce_api.repository.Address.AddressRepository;
import com.example.wecommerce_api.repository.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressServiceImp extends AdressService {
    private final AddressRepository adressRepository;
    private final UserRepository userRepository;
    private final Validation validation;

    public AdressServiceImp(AddressRepository adressRepository, UserRepository userRepository, Validation validation) {
        this.adressRepository = adressRepository;
        this.userRepository = userRepository;
        this.validation = validation;
    }

    @Override
    public AddressEntity AddAddressDel(AddressRequest addressRequest) {
        UserEntity auth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHandler("User is not found!"));
        validation.ValidationInputUserName(addressRequest.getContact());
        validation.ValidationPhoneNumber(addressRequest.getTelephone());
        AddressEntity address = new AddressEntity();
        address.setAddress(addressRequest.getAddress());
        address.setDetail(addressRequest.getDetail());
        address.setContact(addressRequest.getContact());
        address.setLabel(addressRequest.getLabel());
        address.setTelephone(addressRequest.getTelephone());
        address.setUser(user);
        return adressRepository.save(address);
    }

    @Override
    public List<AddressEntity> getAddressDelivery(Integer userId) {
        List<AddressEntity> addressEntities = adressRepository.findByUserIdOrderByIdDesc(userId);
        if (addressEntities.isEmpty()) {
            throw new FieldEmptyExceptionHandler("No record!");
        }
        return addressEntities;
    }

    @Override
    public String updateAddress(Long addressId, AddressRequest addressRequest) {
        UserEntity auth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionHandler("User not found!"));
        AddressEntity address = adressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundExceptionHandler("Address not found!"));
        validation.ValidationInputUserName(addressRequest.getContact());
        address.setAddress(addressRequest.getAddress());
        address.setDetail(addressRequest.getDetail());
        address.setContact(addressRequest.getContact());
        address.setLabel(addressRequest.getLabel());
        address.setTelephone(addressRequest.getTelephone());
        adressRepository.save(address);
        return "Update successful!";
    }

    @Override
    public AddressEntity getAddressDeliveryById(Integer userid, Long id) {
        AddressEntity address = adressRepository.findByIdAndUserId(id, userid);
        if (address == null) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        return address;
    }
}