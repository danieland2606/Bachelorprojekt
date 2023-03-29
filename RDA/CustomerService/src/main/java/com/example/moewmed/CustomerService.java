package com.example.moewmed;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.example.moewmed.database.CustomerRepository;
import com.example.moewmed.entity.CustomerEntity;
import com.example.moewmed.entity.CustomerRequest;
import org.springframework.http.converter.json.MappingJacksonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

public class CustomerService {

    public MappingJacksonValue getCustomer(long id, CustomerRepository cRepository){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findById(id));

        wrapper.setFilters(new SimpleFilterProvider()
        .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
        .setFailOnUnknownId(false));

        return wrapper;
    }

    public MappingJacksonValue getCustomerList(String fields, CustomerRepository cRepository){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findAll());

        List<String> customerList = new ArrayList<String>();
        customerList.addAll(Arrays.asList(fields.split(",")));
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(customerList)))
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

