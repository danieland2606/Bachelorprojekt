package EDA.NotificationService.Persistence;

import EDA.NotificationService.Persistence.Entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}


