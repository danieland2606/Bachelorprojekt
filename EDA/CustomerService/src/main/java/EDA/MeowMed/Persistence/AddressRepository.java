package EDA.MeowMed.Persistence;

import EDA.MeowMed.Persistence.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
