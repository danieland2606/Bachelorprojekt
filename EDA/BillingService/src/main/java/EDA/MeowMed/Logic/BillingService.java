package EDA.MeowMed.Logic;

import event.objects.customer.CustomerCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    private final CustomerRepository customerRepository;

    private final BillRepository billRepository;

    @Autowired
    public PolicyService(BillRepository billRepository, CustomerRepository customerRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
    }

    public void addCustomer(CustomerCreatedEvent customerCreatedEvent) {
        this.customerRepository.save(new Customer(customerCreatedEvent));
    }

    public void addBillForNewlyCreatedPolicy(Policy p) {
        Bill b = new Bill();
        b.setPolicyId(p.getId());
        b.setStartDate(p.getStartDate());
        b.setDueDate(p.getDueDate());
        b.setChargedAmount(p.getPremium() * 12);
        Optional<Customer> customerForPolicy = this.customerRepository.findById(p.getCustomer().getId());
        if (customerForPolicy.isEmpty()) {
            //TODO: throw Exception
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
    }

    public void addBillForUpdatedPolicy(Policy p) {
        double alreadyChargedAmount = 0.0;
        for (Bill b : this.billRepository.getBillsByPolicyId(p.getId())) {
            alreadyChargedAmount += b.getChargedAmount();
        }

        double amountForNewBill = (p.getPremium() * 12) - alreadyChargedAmount;
        Bill b = new Bill();
        b.setPolicyId(p.getId());
        b.setStartDate(p.getStartDate());
        b.setDueDate(p.getDueDate());
        b.setChargedAmount(amountForNewBill);
        Optional<Customer> customerForPolicy = this.customerRepository.findById(p.getCustomer().getId());
        if (customerForPolicy.isEmpty()) {
            //TODO: throw Exception
        }
        b.setCustomer(customerForPolicy.get());
        this.billRepository.save(b);
    }
}