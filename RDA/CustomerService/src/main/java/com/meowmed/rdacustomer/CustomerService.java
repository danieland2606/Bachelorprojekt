package com.meowmed.rdacustomer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdacustomer.database.AddressRepository;
import com.meowmed.rdacustomer.database.CustomerRepository;
import com.meowmed.rdacustomer.entity.CustomerEntity;
import com.meowmed.rdacustomer.entity.CustomerRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

/**
 * Diese Klasse ist die Service-Klasse des REST-Controllers
 *
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis des Git-Repos
 * @author Daniel Arnold, Jan Lorenz
 */
@Service
public class CustomerService {

    private final CustomerRepository cRepository;
    private final AddressRepository aRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository adressRepository) {
        this.cRepository = customerRepository;
        this.aRepository = adressRepository;
    }

    /** TODO
     * Diese Methode nimmt eine ID eines Customers entgegen und gibt ein CustomerEntity zurück
     * @param id ID des gefragten Customers
     * @return CustomerEntity ohne seine ID
     */
    public MappingJacksonValue getCustomer(long id){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findById(id));

        wrapper.setFilters(new SimpleFilterProvider()
        .addFilter("customerFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
        .setFailOnUnknownId(false));
        return wrapper;
    }

    /** TODO
     * Diese Methode nimmt einen String entgegen welcher aus Vorname, Nachname und Addresse bestehen kann.
     * @param fields String aus Vorname, Nachname und Addresse. Attribute sind mit "," getrennt.
     * @return Gibt eine Liste mit CustomerObjekten zurück, welche die gleichen Attribute aus fields haben.
     */
    //?fields=id,firstName,lastName,address
    public MappingJacksonValue getCustomerList(String fields){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findAll());

        List<String> customerList = new ArrayList<String>();
        /* 
        // Im Falle, das address.city benutzt werden soll
        List<String> addressList = new ArrayList<String>();
        boolean containsOoI = false;
		for (String result : customerList) {
			if(result.contains("address.")){
				addressList.add(result.substring(8));
				customerList.remove(result);
				containsOoI = true;
			}
		}
		if(containsOoI) addressList.add("address");
        */
        customerList.addAll(Arrays.asList(fields.split(",")));
        customerList.add("id");
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(customerList)))
                //.addFilter("addressFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(addressList)))
                .addFilter("addressFilter", SimpleBeanPropertyFilter.serializeAll())
                .setFailOnUnknownId(false));
        return wrapper;
    }

    /**
     * Diese Methode speichert ein CustomerEntity.
     * @param cRequest ist das zu speichernde Customer Objekt
     * @return Die ID des erstellten Objekts.
     */
    public MappingJacksonValue postCustomer(CustomerRequest cRequest) {
        aRepository.save(cRequest.getAdress());
        CustomerEntity customer= new CustomerEntity(cRequest.getFirstName(), cRequest.getLastName(), cRequest.getFormOfAdress(), cRequest.getMartialStatus(), cRequest.getDateOfBirth(), cRequest.getEmploymentStatus(), cRequest.getAdress(), cRequest.getPhoneNumber(),cRequest.getEmail(),cRequest.getBankDetails());
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.save(customer));
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                .setFailOnUnknownId(false));
        return wrapper;
    }
}

