package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.Address.AddressRequest;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.Address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("addAddressDelivery")
    public ResponseEntity<?> AddAddressDelivery(@RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(new ApiResponse<>(
                addressService.AddAddressDel(addressRequest),
                "OK",
                200,
                false,
                LocalDateTime.now()

        ));

    }
    @GetMapping("listAddressDelivery")
    public ResponseEntity<?> GetAddressDelivery(){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                addressService.getAddressDelivery(id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @PutMapping("editAddressDelivery/{id}")
    public ResponseEntity<?> UpdateAddress(@PathVariable("id") Long addressId,@RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(new ApiResponse<>(
                addressService.updateAddress(addressId,addressRequest),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));

    }
    @GetMapping("{id}")
    public ResponseEntity<?> GetAddressDeliveryById(@PathVariable Long id){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userid = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                addressService.getAddressDeliveryById(userid,id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
}
