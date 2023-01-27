package com.abdulrehman1793.restapitest.services;

import com.abdulrehman1793.restapitest.domain.Address;
import com.abdulrehman1793.restapitest.web.model.AddressDto;

import java.util.Set;
import java.util.UUID;

public interface AddressService {
    Set<Address> listStudentAddresses(UUID studentId);

    AddressDto createStudentAddress(UUID studentId, AddressDto addressDto);

    AddressDto updateStudentAddress(UUID studentId, UUID addressID, AddressDto addressDto);

    void removeStudentAddress(UUID studentId, UUID addressID);
}
