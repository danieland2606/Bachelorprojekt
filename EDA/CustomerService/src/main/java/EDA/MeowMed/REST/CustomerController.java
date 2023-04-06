package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import EDA.MeowMed.REST.Objects.New_Customer;

import EDA.MeowMed.REST.Objects.Simple_Customer;
import EDA.MeowMed.REST.Objects.View_Customer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
