package com.abdulrehman1793.restapitest.repositories;

import com.abdulrehman1793.restapitest.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
