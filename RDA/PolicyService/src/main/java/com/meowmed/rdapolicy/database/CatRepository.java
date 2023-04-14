package com.meowmed.rdapolicy.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meowmed.rdapolicy.entity.CatEntity;

@Repository
public interface CatRepository extends JpaRepository<CatEntity, Long> {
    CatEntity findByRace(String race);
    //List<PolicyEntity> findByCid(long c_id);
}


