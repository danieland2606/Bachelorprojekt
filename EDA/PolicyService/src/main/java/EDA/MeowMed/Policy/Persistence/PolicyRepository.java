package EDA.MeowMed.Policy.Persistence;


import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
