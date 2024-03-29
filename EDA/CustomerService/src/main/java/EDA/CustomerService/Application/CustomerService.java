package EDA.CustomerService.Application;

import EDA.CustomerService.Messaging.EventSenderService;
import EDA.CustomerService.Persistence.CustomerRepository;
import EDA.CustomerService.Persistence.Entity.Customer;
import EDA.CustomerService.Persistence.Entity.Address;
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
    private CustomerValidationService customerValidationService;

    /**
     * Retrieves a JSON representation of a customer with the specified ID, excluding the "id" field.
     *
     * @param id The ID of the customer.
     * @return The JSON representation of the customer.
     * @throws JsonProcessingException If an error occurs while processing JSON.
     */
    public String getCustomer(Long id) throws JsonProcessingException {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        }
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
                .addFilter("addressFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
        return mapper.writer(filters).writeValueAsString(request.get());
    }

    /**
     * Retrieves a JSON representation of a list of customers based on the specified fields.
     *
     * @param fields The fields to include in the JSON representation. If null, all fields will be included.
     * @return The JSON representation of the list of customers.
     * @throws JsonProcessingException       If an error occurs while processing JSON.
     * @throws IllegalArgumentException     If an invalid field-parameter is provided.
     */
    public String getCustomerList(String fields) throws JsonProcessingException, IllegalArgumentException {
        // request all Customers
        List<Customer> request = customerRepository.findAll();
        // checks if request is empty or not
        if (request.isEmpty()) {
            return null;
        } else {
            // if no fields where transmitted declare as empty
            if (fields == null) {
                fields = "";
            }
            // add "id" as base field to filter by
            fields = "id," + fields;
            // transform to list, by splitting at ","
            List<String> filterList = Arrays.asList(fields.split(","));

            // filters all elements important for customer and address out of the filterList
            Set<String> customerFilter = filterList.stream()
                    .filter(s -> !s.contains("address."))
                    .collect(Collectors.toSet());

            Set<String> addressFilter = filterList.stream()
                    .filter(s -> s.contains("address."))
                    .map(s -> s.replace("address.", ""))
                    .collect(Collectors.toSet());

            // gets all field names for customer and address, and removes "id" from address
            Set<String> customerFields = Arrays.stream(Customer.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());

            Set<String> addressFields = Arrays.stream(Address.class.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toSet());
            addressFields.remove("id");

            // checks if all filter elements are valid
            for (String filter : customerFilter) {
                if (!customerFields.contains(filter))
                    throw new IllegalArgumentException(filter + " is not a valid field-parameter for customer!");
            }

            for (String filter : addressFilter) {
                if (!addressFields.contains(filter))
                    throw new IllegalArgumentException(filter + " is not a valid field-parameter for address!");
            }

            // sets filters for all customers
            FilterProvider filters = new SimpleFilterProvider();

            // if there are elements in address that need to be filtered, but address is not wanted,
            // filter them and add address so those sub-elements can be shown
            if (!customerFilter.contains("address") && !addressFilter.isEmpty()) {
                customerFilter.add("address");
                ((SimpleFilterProvider) filters).addFilter("addressFilter", SimpleBeanPropertyFilter.filterOutAllExcept(addressFilter));
            }
            ((SimpleFilterProvider) filters).addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept(customerFilter));
            ((SimpleFilterProvider) filters).setFailOnUnknownId(false);

            ObjectMapper mapper = new ObjectMapper();

            // sets format for LocalDate
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));

            // apply filter and return filtered list of customers
            return mapper.writer(filters).writeValueAsString(request);
        }
    }

    /**
     * Adds a new customer based on the provided JSON representation.
     *
     * @param jsonCustomer The JSON representation of the customer to be added.
     * @return The JSON representation of the created customer id.
     * @throws JsonProcessingException If an error occurs while processing JSON.
     * @throws IllegalArgumentException If the JSON format is incorrect.
     */
    public String addCustomer(String jsonCustomer) throws JsonProcessingException {
        if (jsonCustomer.contains("\"id\":")) {
            throw new IllegalArgumentException("Wrong Json Format");
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
        Customer customer = mapper.readValue(jsonCustomer, Customer.class);

        customerValidationService.validateCustomer(customer);

        customerRepository.save(customer);
        eventSenderService.sendCustomerCreatedEvent(customer);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"));

        return mapper.writer(filters).writeValueAsString(customer);
    }

    /**
     * Replaces an existing customer with the specified ID based on the provided JSON representation.
     *
     * @param id           The ID of the customer to be replaced.
     * @param jsonCustomer The JSON representation of the customer to replace the existing one.
     * @return A string representation (null) indicating a successful replacement.
     * @throws JsonProcessingException    If an error occurs while processing JSON.
     * @throws IllegalArgumentException  If the JSON format is incorrect.
     * @throws NoSuchElementException    If the customer with the specified ID does not exist.
     */
    public String replaceCustomer(Long id, String jsonCustomer) throws JsonProcessingException, IllegalArgumentException, NoSuchElementException {
        if (jsonCustomer.contains("\"id\":")) {
            throw new IllegalArgumentException("Wrong Json Format");
        }
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            throw new NoSuchElementException("Customer doesn't exist");
        }
        Customer customerOld = request.get();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
        Customer customerNew = mapper.readValue(jsonCustomer, Customer.class);

        customerValidationService.validateCustomer(customerNew);

        customerNew.setId(customerOld.getId());
        customerRepository.save(customerNew);
        
        eventSenderService.sendCustomerChangedEvent(customerNew);

        return null;
    }
}
