package EDA.PolicyService.Logic;

import EDA.PolicyService.Persistence.Entity.CatRace;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CatRaceDatabase {
    ArrayList<CatRace> entities;
    public CatRaceDatabase(){
        entities = new ArrayList<>();
        entities.add(new CatRace("siamese", 12, 15, 4, 7, 2, new String[]{"seal", "blau", "lilac", "creme"}));
        entities.add(new CatRace("perser", 12, 16, 4, 7, 3, new String[]{"weiß", "schildpatt", "schwarz"}));
        entities.add(new CatRace("bengal", 12, 16, 4, 6, 4, new String[]{"braun", "schildpatt", "marmor"}));
        entities.add(new CatRace("maine-cone", 12, 15, 5, 10, 2, new String[]{"grau", "braun", "weiß"}));
        entities.add(new CatRace("sphynx", 12, 15, 4, 6, 5, new String[]{}));
        entities.add(new CatRace("scottish Fold", 12, 15, 4, 6, 6, new String[]{}));
        entities.add(new CatRace("british-shorthair", 12, 15, 4, 6, 0, new String[]{}));
        entities.add(new CatRace("abyssinian", 12, 15, 3, 5, 4, new String[]{"rot", "schildpatt", "zimt"}));
        entities.add(new CatRace("ragdoll", 12, 15, 4, 7, 3, new String[]{"blau", "seal", "lilac", "schildpatt"}));
    }

    public CatRace findByRace(String race) {
        for (CatRace catRace: entities             ) {
            if (catRace.getRace().equals(race))
                return catRace;
        }
        return null;
    }
}
