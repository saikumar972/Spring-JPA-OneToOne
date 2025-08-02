package com.onetoone.utility;

import com.onetoone.dto.AddressDto;
import com.onetoone.entity.Address;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class AddressDtoToEntity implements Function<AddressDto, Address> {
    @Override
    public Address apply(AddressDto addressDto) {
        return Address.builder()
                .district(addressDto.getDistrict())
                .street(addressDto.getStreet())
                .state(addressDto.getState())
                .build();
    }
}
