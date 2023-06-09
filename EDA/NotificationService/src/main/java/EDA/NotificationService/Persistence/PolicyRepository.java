package EDA.NotificationService.Persistence;

import EDA.NotificationService.Persistence.Entity.Customer;
import EDA.NotificationService.Persistence.Entity.Policy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    /**
     * Finds a list of "Policy" objects in the database that belong to the specified customer.
     *     *
     * @param customer The customer whose "Policy" objects are to be retrieved.
     *
     * @return A List of "Policy" objects belonging to the specified customer.
     *
     * @throws DataAccessException If an error occurs while accessing the database.
     *
     */
    public List<Policy> getPolicyList(Customer customer) throws DataAccessException ;

    /**
     * Finds a "Policy" in the database based on its ID and the ID of its associated customer.
     *
     * @param customer The associated Customer
     *
     * @param pid The ID of the "Policy"
     *
     * @return A "Policy" object containing the requested attributes or null if no matching "Policy" was found.
     *
     * @throws DataAccessException If an error occurs while accessing the database.
     */
    public Optional<Policy> findPolicyByCustomerAndPid(Customer customer, Long pid) throws DataAccessException;

}


