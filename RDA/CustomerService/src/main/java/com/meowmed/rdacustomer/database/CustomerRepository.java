package com.meowmed.rdacustomer.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meowmed.rdacustomer.entity.*;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}

