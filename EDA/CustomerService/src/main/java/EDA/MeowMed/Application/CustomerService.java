package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventSenderService;
import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.REST.Objects.New_Customer;
import EDA.MeowMed.REST.Objects.Simple_Customer;
import EDA.MeowMed.REST.Objects.View_Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public View_Customer getCustomer(Long id) {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            return new View_Customer(request.get());
        }
    }

    public List<Simple_Customer> getCustomerList() {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            List<Simple_Customer> customerList = new ArrayList<>();
            for (var customer : request) {
                customerList.add(new Simple_Customer(customer));
            }
            return customerList;
        }
    }

    public Long addCustomer(New_Customer newCustomer) {
        Customer customer = new Customer(newCustomer);
        this.customerRepository.save(customer);
        //this.addressRepository.save(customer.getAddress());
        eventSenderService.sendCustomerCreatedEvent(customer);
        return customer.getId();
    }

}
