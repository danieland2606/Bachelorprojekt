package com.moewmed.rdacustomer.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moewmed.rdacustomer.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    
}
