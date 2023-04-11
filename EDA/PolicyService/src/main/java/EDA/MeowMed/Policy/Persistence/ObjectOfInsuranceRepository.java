package EDA.MeowMed.Policy.Persistence;

import EDA.MeowMed.Policy.Persistence.Entity.ObjectOfInsurance;
import EDA.MeowMed.Policy.View.ObjectOfInsuranceNameOverviewProjection;
import EDA.MeowMed.Policy.View.PolicyOverviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectOfInsuranceRepository extends JpaRepository<ObjectOfInsurance, Long> {

    @Query("SELECT o.name as name FROM ObjectOfInsurance o WHERE o.id = :o_id")
    public ObjectOfInsuranceNameOverviewProjection  findObjectOfInsuranceNameByPolicyID(long o_id);

}
