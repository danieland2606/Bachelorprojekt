package EDA.MeowMed.Policy.Rest;

import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import EDA.MeowMed.Policy.Logic.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import EDA.MeowMed.Policy.View.PolicyOverviewProjection;


@RestController
@RequestMapping("/customer/{c_id}/policy")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }


    @GetMapping
    public MappingJacksonValue getPolicyList(@PathVariable long c_id, @RequestParam(value = "fields") String fields) {
        return this.policyService.getPolicyList(c_id, fields);
    }

    /**
     This method adds a new policy for a customer with the given customerID.
     It first checks if the customer exists in the database by using the
     customerRepository and the provided customerID.

     If the customer is found, the method sets the customer for the provided
     policy and calculates the premium based on certain factors using the
     getPremium() method.

     The policy object is then saved to the policyRepository and the objectOfInsurance
     is saved to the objectOfInsuranceRepository.

     Finally, the policy is sent to a message broker using the policyAddedSender.

     @param customerID - the ID of the customer for which the policy is being added

     @param policy - the policy object being added for the customer

     @return the policy object that was added

     */
    @GetMapping("/{p_id}")
    public ResponseEntity<?> findPolicyByCustomerIDAndPolicyID(@PathVariable long c_id, @PathVariable long p_id) {
        try {
            MappingJacksonValue policy = this.policyService.findPolicyByCustomerIDAndPolicyID(c_id, p_id);
            return ResponseEntity.ok(policy);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry, the requested policy with Customer ID " + c_id + " and Policy ID " + p_id + " could not be found.");
        } catch (Exception e) {
            String errorMessage = "Error while finding the policy with the id " + p_id + " for customer " + c_id +". Please check if  both, policy and customer exists and try again." +"\nMore infos: " + e.getMessage();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    /**
     Adds a new policy for the given customer ID and policy information.

     If the customer is not found, it throws a NotFoundException.

     @param c_id the ID of the customer the policy is added for

     @param policy the policy information to be added

     @return the added policy

     //TODO: Fehlerbehandelung für NotFoundException
     @throws ChangeSetPersister.NotFoundException if the customer with the given ID is not found
     */
    @PostMapping
    public ResponseEntity<?> addPolicy(@PathVariable long c_id, @RequestBody Policy policy) {
        try {
            MappingJacksonValue savedPolicy = this.policyService.addPolicy(c_id, policy);
            return ResponseEntity.ok(savedPolicy);
        } catch (Exception e) {
            String errorMessage = "Error adding policy for customer " + c_id + "\nMore infos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}