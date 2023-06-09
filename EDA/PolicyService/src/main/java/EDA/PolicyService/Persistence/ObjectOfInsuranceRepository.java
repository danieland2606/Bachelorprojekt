package EDA.PolicyService.Persistence;

import EDA.PolicyService.Persistence.Entity.ObjectOfInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectOfInsuranceRepository extends JpaRepository<ObjectOfInsurance, Long> {

}
