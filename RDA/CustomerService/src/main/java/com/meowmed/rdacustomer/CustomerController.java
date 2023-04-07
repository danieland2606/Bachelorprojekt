package com.meowmed.rdacustomer;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.meowmed.rdacustomer.entity.CustomerRequest;


@RestController
public class CustomerController{

    private final CustomerService = cService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.cService =  costumerService;
    }

    /**
     * Diese Methode nimmt die GET-Anfrage für die CustomerListe an und gibt diese gefiltert zurück.
     * @url "http://domain:port/customer"
     * @param fields ist eine Liste von Komma-separierten Daten, diese können aus Vorname, Nachname und Addresse bestehen.
     * @return CONTINUE HERE
     */
    @GetMapping("/customer")
    public MappingJacksonValue getCustomerList(@RequestParam(value = "fields") String fields) {
        return cService.getCustomerList(fields);
    }
    @GetMapping("/customer/{c_id}")
    public MappingJacksonValue getCustomer(@PathVariable Long c_id) {
        return cService.getCustomer(c_id);

    }
    @PostMapping("/customer")
    public MappingJacksonValue postCustomer(@RequestBody CustomerRequest cRequest) {
        return cService.postCustomer(cRequest);

    }
}