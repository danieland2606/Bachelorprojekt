package com.meowmed.rdabilling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.meowmed.rdabilling.database.InvoiceRepository;
import com.meowmed.rdabilling.entity.BillingEntity;
import com.meowmed.rdabilling.entity.BillingPolicyEntity;
import com.meowmed.rdabilling.exceptions.InvoiceNotFoundException;

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
        //setUp();
    }

    @Value("${docker.debugmode}")
	private boolean debugmode;

    /**
     * Diese methode speichert eine Rechnung
     * @param billingPolicyEntity BillingPolicyEntity Objekt
     */
    public void putInvoice(BillingPolicyEntity billingPolicyEntity) {
        // Erstellen einer BillingEntity basierend auf dem BillingPolicyEntity-Objekt
        BillingEntity bill = new BillingEntity(
            billingPolicyEntity.getDueDate(), 
            Math.round(billingPolicyEntity.getMonthlyCost()*12*100.0)/100.0, 
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
    public MappingJacksonValue getInvoiceList(Long c_id, Long p_id){
        List<BillingEntity> liste = iRepository.findByPid(p_id);
        if(liste.isEmpty()) throw new InvoiceNotFoundException("Es existiert kein Invoice");
        List<String> filter = new ArrayList<String>();
        filter.add("id");
        filter.add("dueDate");
        filter.add("amount");
        filter.add("details");
        MappingJacksonValue wrapper = new MappingJacksonValue(iRepository.findByPid(p_id));
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("billingFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(filter)))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("getInvoiceList: wrapper: " + wrapper);
		return wrapper;
        
        //return iRepository.findByPid(p_id);
    }

    void setUp(){
        LocalDate startDate = LocalDate.of(2017, 1, 15);
        BillingEntity entity1 = new BillingEntity(startDate, 1500, "DE92500105177455257131", 1, "Jan Niklas", 1, startDate, "DU mich auch", 1);
        BillingEntity entity2 = new BillingEntity(startDate, 1500, "DE92500105177455257131", 1, "Jan Niklas", 2, startDate, "DU mich auch", 2);
        iRepository.save(entity1);
        iRepository.save(entity2);
    }
}
