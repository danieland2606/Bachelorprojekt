package com.moewmed.rdacustomer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.moewmed.rdacustomer.database.CustomerRepository;
import com.moewmed.rdacustomer.entity.CustomerEntity;
import com.moewmed.rdacustomer.entity.CustomerRequest;

import org.springframework.http.converter.json.MappingJacksonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

public class CustomerService {

    public MappingJacksonValue getCustomer(long id, CustomerRepository cRepository){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findById(id));

        wrapper.setFilters(new SimpleFilterProvider()
        .addFilter("customerFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
        .setFailOnUnknownId(false));
        return wrapper;
    }
    //?fields=id,firstName,lastName,address
    public MappingJacksonValue getCustomerList(String fields, CustomerRepository cRepository){
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
        //customerList.add("id");
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(customerList)))
                //.addFilter("addressFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(addressList)))
                .addFilter("addressFilter", SimpleBeanPropertyFilter.serializeAll())
                .setFailOnUnknownId(false));
        return wrapper;
    }

    public MappingJacksonValue postCustomer(CustomerRequest cRequest, CustomerRepository cRepository) {
        CustomerEntity customer= new CustomerEntity(cRequest.getFirstName(), cRequest.getLastName(), cRequest.getMartialStatus(), cRequest.getDateOfBirth(), cRequest.getEmploymentStatus(), cRequest.getAdress(), cRequest.getPhoneNumber(),cRequest.getEmail(),cRequest.getBankDetails());
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.save(customer));
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                .setFailOnUnknownId(false));
        return wrapper;

    }
}

