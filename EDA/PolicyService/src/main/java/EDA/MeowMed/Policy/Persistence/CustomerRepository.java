package EDA.MeowMed.Policy.Persistence;


import EDA.MeowMed.Policy.Persistence.Entity.Customer;
import EDA.MeowMed.Policy.Persistence.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
