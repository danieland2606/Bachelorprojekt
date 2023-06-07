package EDA.MeowMed.Rest;

import EDA.MeowMed.Exceptions.DatabaseAccessException;
import EDA.MeowMed.Exceptions.InvalidPolicyDataException;
import EDA.MeowMed.Exceptions.ObjectNotFoundException;
import EDA.MeowMed.Exceptions.ErrorResponse;

import EDA.MeowMed.Persistence.Entity.Policy;
import EDA.MeowMed.Logic.PolicyService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     Retrieves the policy object with the given policy ID for the customer with the given customer ID.
     @param c_id - the ID of the customer whose policy is being retrieved
     @param p_id - the ID of the policy being retrieved
     @return the policy object with the given policy ID for the customer with the given customer ID.
     @throws ObjectNotFoundException if the policy with the given policy ID for the customer with the given customer ID is not found
     @throws DatabaseAccessException if there is an error accessing the database while retrieving the policy object
     */
    @GetMapping("/customer/{c_id}/policy/{p_id}")
    public ResponseEntity<?> findPolicyByCustomerIDAndPolicyID(@PathVariable long c_id, @PathVariable long p_id) {
        try {
            MappingJacksonValue policy = this.policyService.findPolicyByCustomerIDAndPolicyID(c_id, p_id);
            return ResponseEntity.ok(policy);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sorry, the requested policy with Customer ID "
                    + c_id + " and Policy ID " + p_id + " could not be found.\nMore info: " + e.getMessage());
        } catch (DatabaseAccessException e) {
            String errorMessage = "Error while finding the policy with the id " + p_id + " for customer " + c_id +
                    ". Please check if  both, policy and customer exists and try again.\nMore info: " + e.getMessage();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "An unexpected error occurred while finding the policy with the id " + p_id +
                    " for customer " + c_id +"\nMore info: " + e.getMessage();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    /**
     This method adds a new policy for a customer with the given customerID.
     It uses a POST request to create a new policy in the database for the given customer ID out of Customer.
     @param c_id - the ID of the customer for which the policy is being added
     @param policy - the policy object being added for the customer
     @return a ResponseEntity containing the newly added policy object
     @throws ObjectNotFoundException if the customer with the given customerID does not exist in the database
     @throws DatabaseAccessException if there was an error accessing the database
     @throws ObjectNotFoundException is thrown when the customer with the given customerID does not exist in the database.
     @throws DatabaseAccessException is thrown when there is an error accessing the database.
     */
    @PostMapping("/customer/{c_id}/policy")
    public ResponseEntity<?> addPolicy(@PathVariable long c_id, @RequestBody Policy policy) {
        try {
            MappingJacksonValue savedPolicy = this.policyService.addPolicy(c_id, policy);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPolicy);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + c_id + " not found \nMore info: " + e.getMessage());
        } catch (DatabaseAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error accessing the database: " + e.getMessage());
        } catch (InvalidPolicyDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            String errorMessage = "Error adding policy for customer " + c_id + "\nMore infos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    /**
     Retrieves a list of policies for the given customer ID, filtered by the specified fields.
     Uses the policyService to retrieve the policy list for the provided customer ID and filters it based on
     the fields parameter using the getPolicyList() method. The resulting policies are returned as a MappingJacksonValue
     wrapped in a ResponseEntity.
     @param c_id - the ID of the customer whose policies are being retrieved
     @param fields - the comma-separated list of fields to be included in the response
     @return a ResponseEntity containing the MappingJacksonValue of the retrieved policies
     @throws ObjectNotFoundException - if the customer does not exist in the database
     @throws DatabaseAccessException - if there is an error accessing the database
     @see PolicyService#getPolicyList(long, String)
     */
    @GetMapping("/customer/{c_id}/policy")
    public ResponseEntity<?> getPolicyList(@PathVariable long c_id, @RequestParam(value = "fields") String fields) {
        try {
            MappingJacksonValue policies = this.policyService.getPolicyList(c_id, fields);
            return ResponseEntity.ok(policies);
        } catch (ObjectNotFoundException e) {
            String errorMessage = "Policy list not found for customer ID " + c_id + "\nMore infos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorMessage));
        } catch (DatabaseAccessException e) {
            String errorMessage = "Error accessing database for policy list of customer ID " + c_id + "\nMore infos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errorMessage));
        }
    }

    /**
     * Calculates the Premium
     * @param premiumCalculationData
     * @return
     */
    @PostMapping("/policyprice")
    public ResponseEntity<?> getPremium(@RequestBody PremiumCalculationData premiumCalculationData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<String>(mapper.writeValueAsString(Collections.singletonMap("premium",
                    this.policyService.getPremium(premiumCalculationData))), HttpStatusCode.valueOf(200));
        } catch (ObjectNotFoundException e) {
            String errorMessage = "Error calculating premium because the customer does not exist\nMore infos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Conversion failed.");
        }
    }

    /**
     * Updates the Policy with p_id
     * @param customerID
     * @param policyID
     * @param policy The Policy Data
     * @return
     */
    @PutMapping("/customer/{c_id}/policy/{p_id}")
    public ResponseEntity<?> changePolicy(@PathVariable("c_id") Long customerID, @PathVariable("p_id") Long policyID, @RequestBody Policy policy) {
        try {
            this.policyService.updatePolicy(policyID, policy);
            return new ResponseEntity<>(HttpStatusCode.valueOf(204));
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<String>("The Policy with ID: " + policyID + " does not exist.",HttpStatusCode.valueOf(404));
        } catch (InvalidPolicyDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}



