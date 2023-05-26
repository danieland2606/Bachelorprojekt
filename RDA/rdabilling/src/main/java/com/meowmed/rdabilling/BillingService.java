package com.meowmed.rdabilling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import com.meowmed.rdabilling.database.InvoiceRepository;
import com.meowmed.rdabilling.entity.BillingEntity;
import com.meowmed.rdabilling.entity.BillingPolicyEntity;

@Service 
public class BillingService {
    private final InvoiceRepository iRepository;

    @Autowired
    public BillingService(InvoiceRepository invoiceRepository) {
        this.iRepository = invoiceRepository;
    }

    public void putInvoice(long invoiceId, BillingPolicyEntity billingPolicyEntity) {
        
        BillingEntity bill = new BillingEntity(billingPolicyEntity.getDueDate(), billingPolicyEntity.getMonthlyCost()*12, billingPolicyEntity.getBankDetails(), billingPolicyEntity.getCid(),
        billingPolicyEntity.getFirstName() + " " + billingPolicyEntity.getLastName(), billingPolicyEntity.getPid(), billingPolicyEntity.getStartDate(),
        billingPolicyEntity.getBillingReason());
        iRepository.save(bill);
    }

    public List<BillingEntity> getInvoiceList(Long c_id, Long p_id){
        return iRepository.findByPid(p_id);
    }
}
