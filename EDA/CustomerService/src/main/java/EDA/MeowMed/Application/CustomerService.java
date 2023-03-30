package EDA.MeowMed.Application;

import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.View.Simple_Customer;
import EDA.MeowMed.View.NoId_Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final ObjectTranslator objectTranslator;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(ObjectTranslator objectTranslator, CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.objectTranslator = objectTranslator; //Does Spring Load this?
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public NoId_Customer getCustomerById(Long id) {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            return objectTranslator.customerToCustomerView(request.get());
        }
    }

    public List<Simple_Customer> getCustomerList() {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            return objectTranslator.customerListToSimple_CustomerList(request);
        }
    }

    public boolean addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        this.addressRepository.save(customer.getAddress());
        return true;
    }

}
