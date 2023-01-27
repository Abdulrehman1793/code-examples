package com.abdulrehman1793.restapitest.web.mappers;

import com.abdulrehman1793.restapitest.domain.Address;
import com.abdulrehman1793.restapitest.web.model.AddressDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface AddressMapper {
    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);
}
