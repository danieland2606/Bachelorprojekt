package com.meowmed.rdapolicy.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;

@Repository
public interface ObjectOfInsuranceRepository extends JpaRepository<ObjectOfInsuranceEntity, Long> {
    
}