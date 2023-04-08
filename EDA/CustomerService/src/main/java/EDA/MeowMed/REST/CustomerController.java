package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import EDA.MeowMed.REST.Objects.New_Customer;
import EDA.MeowMed.REST.Objects.Simple_Customer;
import EDA.MeowMed.REST.Objects.View_Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public List<Simple_Customer> getCustomerList() {
        return customerService.getCustomerList();
    }

    @GetMapping("/customer/{c_id}")
    public View_Customer getCustomer(@PathVariable Long c_id) {
        return customerService.getCustomer(c_id);
    }

    @PostMapping("/customer")
    public Long postCustomer(@RequestBody New_Customer customer) {
        return customerService.addCustomer(customer);
    }
}
