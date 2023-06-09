package EDA.CustomerService.Persistence;

import EDA.CustomerService.Persistence.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

}
