package EDA.MeowMed.Application;

import EDA.MeowMed.JSON.MapFilter;
import EDA.MeowMed.Messaging.EventSenderService;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventSenderService eventSenderService;
    @Autowired
    private MapFilter mapFilter;

    public Map<String, Object> getCustomer(Long id) {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            String fields = "firstName,lastName,formOfAddress,title,maritalStatus,dateOfBirth,employmentStatus,address,phoneNumber,email,bankDetails";
            return mapFilter.filterMap(request.get().toMap(), fields);
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
                customerList.add(mapFilter.filterMap(customer.toMap(), fields));
            }
            return customerList;
        }
    }

    public Map<String, Object> addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        eventSenderService.sendCustomerCreatedEvent(customer);
        return mapFilter.filterMap(customer.toMap(), "id");
    }
}
