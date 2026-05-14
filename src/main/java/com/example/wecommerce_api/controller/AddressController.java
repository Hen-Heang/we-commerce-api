package com.example.wecommerce_api.controller;

import com.example.wecommerce_api.entity.UserEntity;
import com.example.wecommerce_api.payload.Address.AddressRequest;
import com.example.wecommerce_api.response.ApiResponse;
import com.example.wecommerce_api.service.Address.AdressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AdressService adressService;

    public AddressController(AdressService adressService) {
        this.adressService = adressService;
    }
    @PostMapping("addAddressDelivery")
    public ResponseEntity<?> AddAdressDelivery(@RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(new ApiResponse<>(
                adressService.AddAddressDel(addressRequest),
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
                adressService.getAddressDelivery(id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
    @PutMapping("editAddressDelivery/{id}")
    public ResponseEntity<?> UpdateAddress(@PathVariable("id") Long addressId,@RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(new ApiResponse<>(
                adressService.updateAddress(addressId,addressRequest),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));

    }
    @GetMapping("{id}")
    public ResponseEntity<?> GetAddressDeliveryById(@PathVariable("id") Long id){
        UserEntity auth=(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userid = auth.getId();
        return ResponseEntity.ok(new ApiResponse<>(
                adressService.getAddressDeliveryById(userid,id),
                "OK",
                200,
                false,
                LocalDateTime.now()
        ));
    }
}
