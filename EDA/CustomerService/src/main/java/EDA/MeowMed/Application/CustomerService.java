package EDA.MeowMed.Application;

import EDA.MeowMed.Persistence.AddressRepository;
import EDA.MeowMed.Persistence.CustomerRepository;
import EDA.MeowMed.Persistence.Entity.Customer;
import EDA.MeowMed.View.CustomerOverview;
import EDA.MeowMed.View.CustomerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository){
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public CustomerView getCustomerById (Long id) {
        Optional<Customer> request = customerRepository.findById(id);
        if (request.isEmpty()) {
            return null;
        } else {
            return ObjectTranslator.customerToCustomerView(request.get());
        }
    }

    public List<CustomerOverview> getCustomerList () {
        List<Customer> request = customerRepository.findAll();
        if (request.isEmpty()) {
            return null;
        } else {
            return ObjectTranslator.customerListToCustomerOverviewList(request);
        }
    }

    public boolean addCustomer (Customer customer) {
        this.customerRepository.save(customer);
        this.addressRepository.save(customer.getAddress());
        return true;
    }

}
