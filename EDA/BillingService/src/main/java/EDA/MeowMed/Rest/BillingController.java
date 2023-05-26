package EDA.MeowMed.Rest;


import EDA.MeowMed.Logic.BillingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BillingController {
    private final BillingService billingService;

    @Autowired
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/customer/{c_id}/policy/{p_id}/invoice")
    public ResponseEntity<?> findAllBillingsForPolicy(@PathVariable long c_id, @PathVariable long p_id) {
        return ResponseEntity.ok(this.billingService.findByCustomerIdAndPolicyId(c_id, p_id)); //TODO: Fehlerbehandlung
    }
}



