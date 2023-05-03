package EDA.MeowMed.Persistence;

import EDA.MeowMed.Persistence.Entity.CatRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRaceRepository extends JpaRepository<CatRace, Long> {

     CatRace findByRace(String race);
}


