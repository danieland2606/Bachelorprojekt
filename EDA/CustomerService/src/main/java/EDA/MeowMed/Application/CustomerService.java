package EDA.MeowMed.Application;

import EDA.MeowMed.Messaging.EventSender;
import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.REST.Objects.Simple_Customer;
import EDA.MeowMed.REST.Objects.View_Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import EDA.MeowMed.REST.Objects.New_Customer;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    public EventSender eventSender;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        eventSender = new EventSender();
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
        this.addressRepository.save(customer.getAddress());
        //eventSender.sendNewCustomerEvent(customer);
        return customer.getId();
    }

}
