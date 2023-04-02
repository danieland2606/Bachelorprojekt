package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventSender;
import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    public EventSender eventSender;

    @Autowired
    public CustomerService( CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        eventSender = new EventSender();
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            return request.get();
        }
    }

    public List<Customer> getCustomerList() {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            return request;
        }
    }

    public Long addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        this.addressRepository.save(customer.getAddress());
        eventSender.sendNewCustomerEvent(customer);
        return customer.getId();
    }

}
