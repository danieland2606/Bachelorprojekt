package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import EDA.MeowMed.Persistence.Entity.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public List<Map<String, Object>> getCustomerList(@RequestParam(value = "fields", required = false) String fields) {
        if (fields == null) {
            fields = "";
        }
        return customerService.getCustomerList(fields);
    }

    @GetMapping("/customer/{c_id}")
    public Map<String, Object> getCustomer(@PathVariable Long c_id) {
        return customerService.getCustomer(c_id);
    }

    @PostMapping("/customer")
    public Map<String, Object> postCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }
}
