package EDA.PolicyService.Persistence;

import EDA.PolicyService.Persistence.Entity.CatRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRaceRepository extends JpaRepository<CatRace, Long> {

     /**
      * Finds a CatRace by the race name
      * @param race The name to search for
      * @return The CatRace
      */
     CatRace findByRace(String race);
}


