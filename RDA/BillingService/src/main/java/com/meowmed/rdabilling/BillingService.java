package com.meowmed.rdabilling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meowmed.rdabilling.database.InvoiceRepository;
import com.meowmed.rdabilling.entity.BillingEntity;
import com.meowmed.rdabilling.entity.BillingPolicyEntity;

/**
 * Diese Klasse ist die Service-Klasse des REST-Controllers
 * 
 * @author Alexander Hampel, Mozamil Ahmadzaei
 */
@Service 
public class BillingService {
    private final InvoiceRepository iRepository;

    @Autowired
    public BillingService(InvoiceRepository invoiceRepository) {
        this.iRepository = invoiceRepository;
    }

    /**
     * Diese methode speichert eine Rechnung
     * @param billingPolicyEntity BillingPolicyEntity Objekt
     */
    public void putInvoice(BillingPolicyEntity billingPolicyEntity) {
        // Erstellen einer BillingEntity basierend auf dem BillingPolicyEntity-Objekt
        BillingEntity bill = new BillingEntity(
            billingPolicyEntity.getDueDate(), 
            billingPolicyEntity.getMonthlyCost()*12, 
            billingPolicyEntity.getBankDetails(), 
            billingPolicyEntity.getCid(),
            billingPolicyEntity.getFirstName() + " " + billingPolicyEntity.getLastName(), 
            billingPolicyEntity.getPid(), 
            billingPolicyEntity.getStartDate(),
            billingPolicyEntity.getBillingReason());
        iRepository.save(bill);
    }

    /**
     * Diese Methode gibt eine Liste von Rechnungen für einen bestimmten Kunden und eine bestimmte Policy zurück.
     * @param c_id die ID des Kunden
     * @param p_id die ID der Policy
     * @return eine Liste von BillingEntity-Objekten
     */
    public List<BillingEntity> getInvoiceList(Long c_id, Long p_id){
        return iRepository.findByPid(p_id);
    }
}
