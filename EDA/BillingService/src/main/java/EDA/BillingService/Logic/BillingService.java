package EDA.BillingService.Logic;

import EDA.BillingService.Messaging.EventSenderService;
import EDA.BillingService.Persistence.BillRepository;
import EDA.BillingService.Persistence.CustomerRepository;
import EDA.BillingService.Persistence.Entity.Bill;
import EDA.BillingService.Persistence.Entity.Customer;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BillingService {
    @Autowired
    EventSenderService eventSenderService;
    private final CustomerRepository customerRepository;

    private final BillRepository billRepository;

    @Autowired
    public BillingService(BillRepository billRepository, CustomerRepository customerRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
    }

    //TODO: Doccomment
    public void addCustomer(CustomerEvent customerEvent) {
        this.customerRepository.save(new Customer(customerEvent));
    }

    //TODO: Doccomment
    public void addBillForNewlyCreatedPolicy(PolicyEvent policyEvent) throws NoSuchElementException{
        double chargedAmount = policyEvent.getPremium() * 12;
        if (Math.abs(chargedAmount) < 0.001) {
            return; // Dont create a Bill when the charged amount is 0 because this would not make any sense
        }
        Bill b = new Bill();
        b.setPolicyId(policyEvent.getPid());
        b.setStartDate(policyEvent.getStartDate());
        b.setDueDate(policyEvent.getDueDate());
        b.setChargedAmount(chargedAmount);
        Optional<Customer> customerForPolicy = this.customerRepository.findById(policyEvent.getCid());
        if (customerForPolicy.isEmpty()) {
            throw new NoSuchElementException("Kunde mit ID: " + policyEvent.getCid() + " Existiert nicht!"); //TODO: Sind alle Rückgabewerte Englisch/Deutsch? Sollte man generell im ganzen Projekt nochmal prüfen ob das einheitlich ist
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
        eventSenderService.sendPolicyCreatedBill(policyEvent.getPid());
    }

    //TODO: Doccomment
    public void addBillForUpdatedPolicy(PolicyEvent policyEvent) throws NoSuchElementException{
        double alreadyChargedAmount = 0.0;
        for (Bill b : this.billRepository.getBillsByPolicyId(policyEvent.getPid())) {
            alreadyChargedAmount += b.getChargedAmount();
        }
        double amountForNewBill = (policyEvent.getPremium() * 12) - alreadyChargedAmount;
        if (Math.abs(amountForNewBill) < 0.001) {
            return; // Dont create a Bill when the charged amount is 0 because this would not make any sense
        }
        Bill b = new Bill();
        b.setPolicyId(policyEvent.getPid());
        b.setStartDate(policyEvent.getStartDate());
        b.setDueDate(policyEvent.getDueDate());
        b.setChargedAmount(amountForNewBill);
        Optional<Customer> customerForPolicy = this.customerRepository.findById(policyEvent.getCid());
        if (customerForPolicy.isEmpty()) {
            throw new NoSuchElementException("Kunde mit ID: " + policyEvent.getCid() + " Existiert nicht!");
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
        eventSenderService.sendPolicyChangedBill(policyEvent.getPid());
    }

    //TODO: Doccomment
    public List<Bill> findBillByCustomerIdAndPolicyId(long customerId, long policyId) throws DataAccessException {
        return this.billRepository.getBillsByCustomerIdAndPolicyId(customerId, policyId);
    }
}