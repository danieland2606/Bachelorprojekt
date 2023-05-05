package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventSenderService;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Address;
import EDA.MeowMed.Persistence.Entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventSenderService eventSenderService;
    @Autowired
    private CustomerValidatorService customerValidatorService;

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
     * @throws IllegalArgumentException
     */
    public String getCustomerList(String fields) throws JsonProcessingException, IllegalArgumentException {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            fields = "id," + fields;
            List<String> filterList = Arrays.asList(fields.split(","));

            //
            Set<String> customerFilter = filterList.stream()
                    .filter(s -> !s.contains("address."))
                    .collect(Collectors.toSet());

            Set<String> addressFilter = filterList.stream()
                    .filter(s -> s.contains("address."))
                    .map(s -> s.replace("address.", ""))
                    .collect(Collectors.toSet());

            //
            Set<String> customerFields = Arrays.stream(Customer.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());

            Set<String> addressFields = Arrays.stream(Address.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());
            addressFields.remove("id");

            for (String filter : customerFilter) {
                if (!customerFields.contains(filter))
                    throw new IllegalArgumentException(filter + " is not a valid field-parameter for customer!");
            }

            for (String filter : addressFilter) {
                if (!addressFields.contains(filter))
                    throw new IllegalArgumentException(filter + " is not a valid field-parameter for address!");
            }

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
     * @param jsonCustomer
     * @return
     * @throws JsonProcessingException
     */
    public String addCustomer(String jsonCustomer) throws JsonProcessingException {
        if (jsonCustomer.contains("\"id\":")) {
            throw new IllegalArgumentException("Wrong Json Format");
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
        Customer customer = mapper.readValue(jsonCustomer, Customer.class);

        customerValidatorService.validateCustomer(customer);

        this.customerRepository.save(customer);
        eventSenderService.sendCustomerCreatedEvent(customer);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"));

        return mapper.writer(filters).writeValueAsString(customer);
    }
}
