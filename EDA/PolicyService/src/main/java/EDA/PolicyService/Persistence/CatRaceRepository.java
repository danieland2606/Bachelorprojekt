package EDA.PolicyService.Persistence;

import EDA.PolicyService.Persistence.Entity.CatRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRaceRepository extends JpaRepository<CatRace, Long> {

     CatRace findByRace(String race);
}


