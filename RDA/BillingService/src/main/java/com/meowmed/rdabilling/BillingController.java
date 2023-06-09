package com.meowmed.rdabilling;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meowmed.rdabilling.BillingService;
import com.meowmed.rdabilling.entity.BillingPolicyEntity;
import com.meowmed.rdabilling.exceptions.InvoiceNotFoundException;

@RestController
public class BillingController {

    private final BillingService bService;

    @Autowired
    public BillingController(BillingService billingService) {
        this.bService = billingService;
    }
    /**
     * Diese Methode handhabt den PUT-Aufruf zum Erstellen einer Rechnung für einen Kunden und eine Policy.
     * @param invoice Das billingPolicyEntity-Objekt für die Rechnung
     */
    @PutMapping("/customer/{c_id}/policy/{p_id}/invoice")
    public ResponseEntity<String> putInvoice(@RequestBody BillingPolicyEntity invoice) {
        bService.putInvoice(invoice);
        return ResponseEntity.status(200).body("Success");
    }
    /**
     * Diese Methode handhabt den GET-Aufruf zum Abrufen der Rechnung für einen bestimmten Kunden und eine Policy.
     * @param c_id die ID des Kunden
     * @param p_id die ID der Policy
     * @return eine Responseentity mit der Liste von BillingEntity-Objekten als Body
     */
    @GetMapping("/customer/{c_id}/policy/{p_id}/invoice")
    public ResponseEntity<MappingJacksonValue> getInvoice(@PathVariable("c_id") Long c_id, @PathVariable("p_id") Long p_id) {
        try {
            return ResponseEntity.status(200).body(bService.getInvoiceList(c_id, p_id));
        } catch (InvoiceNotFoundException e) {
            return ResponseEntity.status(204).body(new MappingJacksonValue(Collections.singletonMap("message", e.getMessage())));
        }
        
        //return bService.putInvoice(invoice);
    }
}