package EDA.MeowMed.Persistence;


import EDA.MeowMed.Persistence.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.policyId = ?1")
    public List<Bill> getBillsByPolicyId(long policyId) throws DataAccessException ;
}
