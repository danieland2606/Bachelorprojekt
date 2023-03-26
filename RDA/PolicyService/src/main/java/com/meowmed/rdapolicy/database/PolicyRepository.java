package com.meowmed.rdapolicy.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meowmed.rdapolicy.entity.PolicyEntity;

public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {
    
}
