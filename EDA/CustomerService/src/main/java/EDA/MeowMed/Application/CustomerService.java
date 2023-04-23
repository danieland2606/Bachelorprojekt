package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventSenderService;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventSenderService eventSenderService;

    /**
     * TODO: Add comment
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */
    public String getCustomer(Long id) throws JsonProcessingException {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            FilterProvider filters = new SimpleFilterProvider()
                    .addFilter("customerFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
                    .addFilter("addressFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
            return mapper.writer(filters).writeValueAsString(request.get());
        }
    }

    /**
     * TODO: Add comment
     *
     * @param fields
     * @return
     * @throws JsonProcessingException
     */
    public String getCustomerList(String fields) throws JsonProcessingException {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            fields = "id," + fields;
            List<String> filter = Arrays.asList(fields.split(","));
            filter.stream().filter(s -> s.contains(".")).collect(Collectors.toSet());
            Set<String> addressFilter = filter.stream().filter(s -> s.contains(".")).map(s -> s.replace("address.", "")).collect(Collectors.toSet());
            Set<String> customerFilter = filter.stream().filter(s -> !s.contains(".")).collect(Collectors.toSet());

            FilterProvider filters = new SimpleFilterProvider();
            if (!customerFilter.contains("address") && !addressFilter.isEmpty()) {
                customerFilter.add("address");
                ((SimpleFilterProvider) filters).addFilter("addressFilter", SimpleBeanPropertyFilter.filterOutAllExcept(addressFilter));
            }
            ((SimpleFilterProvider) filters).addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept(customerFilter));
            ((SimpleFilterProvider) filters).setFailOnUnknownId(false);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
            return mapper.writer(filters).writeValueAsString(request);
        }
    }

    /**
     * TODO: Add comment
     *
     * @param customer
     * @return
     * @throws JsonProcessingException
     */
    public String addCustomer(Customer customer) throws JsonProcessingException {
        this.customerRepository.save(customer);
        eventSenderService.sendCustomerCreatedEvent(customer);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"));

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writer(filters).writeValueAsString(customer);
    }
}
