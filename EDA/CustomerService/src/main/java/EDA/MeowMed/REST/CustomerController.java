package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import EDA.MeowMed.Persistence.Entity.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
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

    /**
     * TODO: Add comment & ResponseEntity
     *
     * @param fields
     * @return
     */
    @GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomerList(@RequestParam(value = "fields", required = false) String fields) {
        if (fields == null) {
            fields = "";
        }
        try {
            return customerService.getCustomerList(fields);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO: Add comment & ResponseEntity
     *
     * @param c_id
     * @return
     */
    @GetMapping(value = "/customer/{c_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCustomer(@PathVariable Long c_id) {
        try {
            return customerService.getCustomer(c_id);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO: Add comment & ResponseEntity
     *
     * @param customer
     * @return
     */
    @PostMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postCustomer(@RequestBody Customer customer) {
        try {
            return customerService.addCustomer(customer);
        } catch
        (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
