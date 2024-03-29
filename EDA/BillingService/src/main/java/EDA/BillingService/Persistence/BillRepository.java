package EDA.BillingService.Persistence;


import EDA.BillingService.Persistence.Entity.Bill;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    /**
     * Find all Bills by policyId
     * @param policyId The policyId
     * @return A list of Bills
     * @throws DataAccessException When something goes wrong while accessing the database
     */
    @Query("SELECT b FROM Bill b WHERE b.policyId = ?1")
    public List<Bill> getBillsByPolicyId(long policyId) throws DataAccessException;

    /**
     * Find all Bills by policyId and customerId
     * @param customerId The customerId
     * @param policyId The policyId
     * @return A list of Bills
     * @throws DataAccessException When something goes wrong while accessing the database
     */
    @Query("SELECT b FROM Bill b JOIN Customer c ON b.customer.id = c.id WHERE c.id = ?1 AND b.policyId = ?2")
    public List<Bill> getBillsByCustomerIdAndPolicyId(long customerId, long policyId) throws DataAccessException;
}