package EDA.MeowMed.Logic;

import EDA.MeowMed.Persistence.Entity.Bill;
import EDA.MeowMed.Persistence.Entity.Customer;
import event.objects.customer.CustomerCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import EDA.MeowMed.Persistence.*;
import event.objects.policy.subclasses.PolicyPojo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BillingService {

    private final CustomerRepository customerRepository;

    private final BillRepository billRepository;

    @Autowired
    public BillingService(BillRepository billRepository, CustomerRepository customerRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
    }

    //TODO: Doccomment
    public void addCustomer(CustomerCreatedEvent customerCreatedEvent) {
        this.customerRepository.save(new Customer(customerCreatedEvent));
    }

    //TODO: Doccomment
    public void addBillForNewlyCreatedPolicy(PolicyPojo p) throws NoSuchElementException{
        double chargedAmount = p.getPremium() * 12;
        if (Math.abs(chargedAmount) < 0.001) {
            return; // Dont create a Bill when the charged amount is 0 because this would not make any sense
        }
        Bill b = new Bill();
        b.setPolicyId(p.getId());
        b.setStartDate(p.getStartDate());
        b.setDueDate(p.getDueDate());
        b.setChargedAmount(chargedAmount);
        Optional<Customer> customerForPolicy = this.customerRepository.findById(p.getCustomer().getId());
        if (customerForPolicy.isEmpty()) {
            throw new NoSuchElementException("Kunde mit ID: " + p.getCustomer().getId() + " Existiert nicht!"); //TODO: Sind alle Rückgabewerte Englisch/Deutsch? Sollte man generell im ganzen Projekt nochmal prüfen ob das einheitlich ist
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
    }

    //TODO: Doccomment
    public void addBillForUpdatedPolicy(PolicyPojo p) throws NoSuchElementException{
        double alreadyChargedAmount = 0.0;
        for (Bill b : this.billRepository.getBillsByPolicyId(p.getId())) {
            alreadyChargedAmount += b.getChargedAmount();
        }
        double amountForNewBill = (p.getPremium() * 12) - alreadyChargedAmount;
        if (Math.abs(amountForNewBill) < 0.001) {
            return; // Dont create a Bill when the charged amount is 0 because this would not make any sense
        }
        Bill b = new Bill();
        b.setPolicyId(p.getId());
        b.setStartDate(p.getStartDate());
        b.setDueDate(p.getDueDate());
        b.setChargedAmount(amountForNewBill);
        Optional<Customer> customerForPolicy = this.customerRepository.findById(p.getCustomer().getId());
        if (customerForPolicy.isEmpty()) {
            throw new NoSuchElementException("Kunde mit ID: " + p.getCustomer().getId() + " Existiert nicht!");
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
    }

    //TODO: Doccomment
    public List<Bill> findBillByCustomerIdAndPolicyId(long customerId, long policyId) throws DataAccessException {
        return this.billRepository.getBillsByCustomerIdAndPolicyId(customerId, policyId);
    }
}