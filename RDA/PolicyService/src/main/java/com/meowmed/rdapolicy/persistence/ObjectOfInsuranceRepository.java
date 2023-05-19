package com.meowmed.rdapolicy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meowmed.rdapolicy.persistence.entity.ObjectOfInsuranceEntity;

@Repository
public interface ObjectOfInsuranceRepository extends JpaRepository<ObjectOfInsuranceEntity, Long> {
    
}