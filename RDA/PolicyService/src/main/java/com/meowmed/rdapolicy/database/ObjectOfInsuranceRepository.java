package com.meowmed.rdapolicy.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;

public interface ObjectOfInsuranceRepository extends JpaRepository<ObjectOfInsuranceEntity, Long> {
    
}