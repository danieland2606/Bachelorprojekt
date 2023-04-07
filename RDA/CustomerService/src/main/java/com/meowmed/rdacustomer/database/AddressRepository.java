package com.meowmed.rdacustomer.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meowmed.rdacustomer.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    
}
