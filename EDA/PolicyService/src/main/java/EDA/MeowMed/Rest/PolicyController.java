package EDA.MeowMed.Rest;

import EDA.MeowMed.Persistence.Entity.Policy;
import EDA.MeowMed.Logic.PolicyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;

@RestController
@RequestMapping("")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }


    @GetMapping("/customer/{c_id}/policy")
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

     @param c_id - the ID of the customer for which the policy is being added

     @param p_id - the policy object being added for the customer

     @return the policy object that was added
     */
    @GetMapping("/customer/{c_id}/policy/{p_id}")
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
     */
    @PostMapping("/customer/{c_id}/policy")
    public ResponseEntity<?> addPolicy(@PathVariable long c_id, @RequestBody Policy policy) {
        try {
            MappingJacksonValue savedPolicy = this.policyService.addPolicy(c_id, policy);
            return ResponseEntity.ok(savedPolicy);
        } catch (Exception e) {
            String errorMessage = "Error adding policy for customer " + c_id + "\nMore infos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/policyprice")
    public ResponseEntity<?> getPremium(@RequestParam("details") String details) {
        ObjectMapper mapper = new ObjectMapper();
        String str = new String(Base64.getUrlDecoder().decode(details), Charset.forName("UTF-8"));
        PremiumCalculationData body = null;
        try {
            mapper.registerModule(new JavaTimeModule());
            body = mapper.readValue(str,PremiumCalculationData.class);
            if (body==null){
                return new ResponseEntity<String>("Convert was not Possible", HttpStatusCode.valueOf(500));
            }
            try {
                return new ResponseEntity<String>(mapper.writeValueAsString(Collections.singletonMap("premium", this.policyService.getPremiumByCalculationObject(body))), HttpStatusCode.valueOf(200));
            } catch (ChangeSetPersister.NotFoundException e) {
                String errorMessage = "Error calculating premium because the customer does not exist\nMore infos: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>("Convert was not Possible",HttpStatusCode.valueOf(500));
    }
}