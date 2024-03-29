package EDA.BillingService.Logic;

import EDA.BillingService.Messaging.EventSenderService;
import EDA.BillingService.Persistence.BillRepository;
import EDA.BillingService.Persistence.CustomerRepository;
import EDA.BillingService.Persistence.Entity.Bill;
import EDA.BillingService.Persistence.Entity.BillUI;
import EDA.BillingService.Persistence.Entity.Customer;
import event.objects.customer.CustomerEvent;
import event.objects.policy.PolicyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * Adds a new customer to the database
     * @param customerEvent The customer Event to retrieve the customer data from
     */
    public void addCustomer(CustomerEvent customerEvent) {
        this.customerRepository.save(new Customer(customerEvent));
    }

    /**
     * Adds a Bill for a newly created Policy
     * @param policyEvent The policy Event with the policy data
     * @throws NoSuchElementException When the specific customer does not exist in the database
     */
    public void addBillForNewlyCreatedPolicy(PolicyEvent policyEvent) throws NoSuchElementException{
        double chargedAmount = policyEvent.getPremium() * 12;
        if (Math.abs(chargedAmount) < 0.001) {
            eventSenderService.sendPolicyCreatedBill(policyEvent.getPid());
            return; // Don't create a Bill when the charged amount is 0 because this would not make any sense
        }
        
        Bill b = new Bill();
        b.setPolicyId(policyEvent.getPid());
        b.setStartDate(policyEvent.getStartDate());
        b.setDueDate(policyEvent.getDueDate());
        chargedAmount = Math.round(chargedAmount*100.0)/100.0;
        b.setChargedAmount(chargedAmount);
        b.setReason("Vertragserstellung");
        Optional<Customer> customerForPolicy = this.customerRepository.findById(policyEvent.getCid());
        if (customerForPolicy.isEmpty()) {
            throw new NoSuchElementException("Customer with ID: " + policyEvent.getCid() + " does not exist");
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
        eventSenderService.sendPolicyCreatedBill(policyEvent.getPid());
    }

    /**
     * Adds a Bill for an updated Policy. The charged amount on the bill will be calculated with the new premium
     * and the charged amounts on all Bills of the specific policy that has been created in the past
     * @param policyEvent The policy Event with the policy data
     * @throws NoSuchElementException When the specific customer does not exist in the database
     */
    public void addBillForUpdatedPolicy(PolicyEvent policyEvent) throws NoSuchElementException{
        double alreadyChargedAmount = 0.0;
        for (Bill b : this.billRepository.getBillsByPolicyId(policyEvent.getPid())) {
            alreadyChargedAmount += b.getChargedAmount();
        }
        double amountForNewBill = (policyEvent.getPremium() * 12) - alreadyChargedAmount;
        if (Math.abs(amountForNewBill) < 0.001) {
            eventSenderService.sendPolicyChangedBill(policyEvent.getPid());
            return; // Dont create a Bill when the charged amount is 0 because this would not make any sense
        }
        Bill b = new Bill();
        b.setPolicyId(policyEvent.getPid());
        b.setStartDate(policyEvent.getStartDate());
        b.setDueDate(policyEvent.getDueDate());
        amountForNewBill = Math.round(amountForNewBill*100.0)/100.0;
        b.setChargedAmount(amountForNewBill); 
        if(amountForNewBill<0){
            b.setReason("Rückbuchung wegen Vertragsänderung");
        }else{
            b.setReason("Abbuchung wegen Vertragsänderung");
        }
        Optional<Customer> customerForPolicy = this.customerRepository.findById(policyEvent.getCid());
        if (customerForPolicy.isEmpty()) {
            throw new NoSuchElementException("Customer with ID: " + policyEvent.getCid() + " does not exist");
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
        eventSenderService.sendPolicyChangedBill(policyEvent.getPid());
    }

    /**
     * Finds all Bills by customerId and policyId
     * @param customerId The customerId
     * @param policyId The policyId
     * @return List of all found Bills
     * @throws DataAccessException When something goes wrong while accessing the database
     */
    public List<BillUI> findBillByCustomerIdAndPolicyId(long customerId, long policyId) throws DataAccessException {
        ArrayList<BillUI> uiList = new ArrayList<>();
        for(Bill bill : this.billRepository.getBillsByCustomerIdAndPolicyId(customerId, policyId)){
            uiList.add(new BillUI(bill));
        }
        return uiList;
        //return this.billRepository.getBillsByCustomerIdAndPolicyId(customerId, policyId);
    }
}
