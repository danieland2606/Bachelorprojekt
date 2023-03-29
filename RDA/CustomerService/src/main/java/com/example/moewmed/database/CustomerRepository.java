package com.example.moewmed.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moewmed.entity.*;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}

