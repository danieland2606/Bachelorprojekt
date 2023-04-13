package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventSenderService;
import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final EventSenderService eventSenderService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository, EventSenderService eventSenderService) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.eventSenderService = eventSenderService;
    }

    public Map<String, Object> getCustomer(Long id) {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            String fields = "firstName,lastName,formOfAddress,title,maritalStatus,dateOfBirth,employmentStatus,address,phoneNumber,email,bankDetails";
            return filterCustomer(request.get().toMap(), fields);
        }
    }

    public List<Map<String, Object>> getCustomerList(String fields) {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            fields = "id," + fields;
            List<Map<String, Object>> customerList = new ArrayList<>();
            for (Customer customer : request) {
                customerList.add(filterCustomer(customer.toMap(), fields));
            }
            return customerList;
        }
    }

    public Long addCustomer(Customer customer) {
        //Customer customer = new Customer(newCustomer);
        this.customerRepository.save(customer);
        //this.addressRepository.save(customer.getAddress());
        eventSenderService.sendCustomerCreatedEvent(customer);
        return customer.getId();
    }

    private Map<String, Object> filterCustomer(Map<String, Object> customer, String fields) {
        Map<String, Object> filtered = new LinkedHashMap<>();
        List<String> fitlerList = Arrays.asList(fields.split(","));
        for (String filter : fitlerList) {
            if (!customer.containsKey(filter) && !filter.contains("address")) {
                throw new IllegalArgumentException("\"" + filter + "\" is not a valid argument for customer!");
            } else if (filter.contains("address") && !filtered.containsKey("address")) {
                String addressFields = fitlerList.stream()
                        .filter(s -> s.contains("address"))
                        .collect(Collectors.joining(","));
                filtered.put("address", filterAddress((Map<String, Object>) customer.get("address"), addressFields));
            } else if (!filter.contains("address")) {
                filtered.put(filter, customer.get(filter));
            }
        }
        return filtered;
    }

    private Map<String, Object> filterAddress(Map<String, Object> address, String fields) {
        List<String> fitlerList = Arrays.asList(fields.split(","));
        if (fitlerList.contains("address")) { //If address.x returns the complete address object, this doesn't work
            return address;
        }

        Map<String, Object> filtered = new LinkedHashMap<>();
        for (String filter : fitlerList) {
            filter = filter.replace("address.", "");
            if (!address.containsKey(filter)) {
                throw new IllegalArgumentException("\"" + filter + "\" is not a valid argument for address!");
            } else {
                filtered.put(filter, address.get(filter));
            }
        }
        return filtered;
    }
}
