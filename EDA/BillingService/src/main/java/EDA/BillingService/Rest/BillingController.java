package EDA.BillingService.Rest;


import EDA.BillingService.Logic.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BillingController {
    private final BillingService billingService;

    @Autowired
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    /**
     * Find all Billings for a specific policy
     * @param c_id The customer id
     * @param p_id The policy id
     * @return HTTP Status Code: OK with all the Billings or HTTP Status Code: BAD REQUEST if something goes wrong
     */
    @GetMapping("/customer/{c_id}/policy/{p_id}/invoice")
    public ResponseEntity<?> findAllBillingsForPolicy(@PathVariable long c_id, @PathVariable long p_id) {
        try {
            var output= this.billingService.findBillByCustomerIdAndPolicyId(c_id, p_id);
            if (output == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(output);
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body("Either customer or policy under given id doesn't exist.");
        }
    }
}



