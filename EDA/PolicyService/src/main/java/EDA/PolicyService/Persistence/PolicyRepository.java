package EDA.PolicyService.Persistence;

import EDA.PolicyService.Persistence.Entity.Policy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    /**
     * Finds a list of "Policy" objects in the database that belong to the specified customer.
     *
     * The method executes a JPQL query to load the attributes "id", "coverage", "startDate", "endDate",
     * "objectOfInsurance", and "premium" of the "Policy" objects that belong to the customer with the given ID.
     *
     * @param customerID The ID of the customer whose "Policy" objects are to be retrieved.
     *
     * @return A List of "Policy" objects belonging to the specified customer.
     *
     * @throws DataAccessException If an error occurs while accessing the database.
     *
     */
    @Query("SELECT p FROM Policy p WHERE p.customer.id = ?1")
    List<Policy> getPolicyList(long customerID) throws DataAccessException ;

    /**
     * Finds a "Policy" in the database based on its ID and the ID of its associated Customer.
     *
     * The method executes a JPQL query to load the attributes "id", "coverage", "startDate", "endDate"
     * ,"objectOfInsurance", and "premium" of the "Policy" that matches the given criteria.
     *
     * @param customerID The ID of the associated Customer
     *
     * @param policyID The ID of the "Policy"
     *
     * @return A "Policy" object containing the requested attributes or null if no matching "Policy" was found.
     *
     * @throws DataAccessException If an error occurs while accessing the database.
     */
    @Query("SELECT p FROM Policy p WHERE p.customer.id = ?1 AND p.id = ?2")
    Policy findPolicyByCustomerIDAndPolicyID(long customerID, long policyID) throws DataAccessException;

}


