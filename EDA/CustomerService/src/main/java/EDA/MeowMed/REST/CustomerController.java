package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * TODO: Add comment
     *
     * @param fields
     * @return
     */
    @GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerList(@RequestParam(value = "fields", required = false) String fields) {
        try {
            String responseBody = customerService.getCustomerList(fields);
            if (responseBody == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(responseBody);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(errorJsonBody(e.getMessage()));
        }
    }

    /**
     * TODO: Add comment
     *
     * @param c_id
     * @return
     */
    @GetMapping(value = "/customer/{c_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomer(@PathVariable Long c_id) {
        try {
            String responseBody = customerService.getCustomer(c_id);
            if (responseBody == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(responseBody);

        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body("Problem processing JSON. Please contact support");
        }
    }

    /**
     * TODO: Add comment
     *
     * @param customer
     * @return
     */
    @PostMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCustomer(@RequestBody String customer) {
        try {
            String responseBody = customerService.addCustomer(customer);
            return ResponseEntity.ok(responseBody);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(errorJsonBody(e.getMessage()));
        }
    }

    private String errorJsonBody(String message) {
        return "{\"error\":\"" + message + "\"}";
    }
}
