package com.example.moewmed.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moewmed.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    
}
