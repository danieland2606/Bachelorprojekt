package com.meowmed.rdacustomer.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meowmed.rdacustomer.entity.*;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}

