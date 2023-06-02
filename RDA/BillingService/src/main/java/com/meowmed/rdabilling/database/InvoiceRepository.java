package com.meowmed.rdabilling.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meowmed.rdabilling.entity.*;


public interface InvoiceRepository extends JpaRepository<BillingEntity, Long> {
    List<BillingEntity> findByPid(long pid);
}

