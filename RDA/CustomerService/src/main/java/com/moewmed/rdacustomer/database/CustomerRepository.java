package com.moewmed.rdacustomer.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moewmed.rdacustomer.entity.*;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}

