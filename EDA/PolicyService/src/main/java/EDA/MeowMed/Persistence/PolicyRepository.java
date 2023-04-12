package EDA.MeowMed.Persistence;

import EDA.MeowMed.Persistence.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    @Query("SELECT p FROM Policy p JOIN ObjectOfInsurance o ON p.objectOfInsurance.id = o.id WHERE p.customer.id = ?1")
    public List<Policy> getPolicyList(long customerID);

    /**
     Finds a "Policy" in the database based on its ID and the ID of its associated Customer.

     The method executes a JPQL query to load the attributes "id", "coverage", "startDate", "endDate"
     ,"objectOfInsurance", and "premium" of the "Policy" that matches the given criteria.

     @param customerID The ID of the associated Customer

     @param policyID The ID of the "Policy"

     @return A "Policy" object containing the requested attributes or null if no matching "Policy" was found.
     */
    @Query("SELECT p FROM Policy p WHERE p.customer.id = ?1 AND p.id = ?2")
    public Policy findPolicyByCustomerIDAndPolicyID(long customerID, long policyID);
}
