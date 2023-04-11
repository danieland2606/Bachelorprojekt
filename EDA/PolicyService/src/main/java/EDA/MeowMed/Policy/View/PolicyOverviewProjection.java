package EDA.MeowMed.Policy.View;

import EDA.MeowMed.Policy.Persistence.Entity.ObjectOfInsurance;

import java.time.LocalDate;

public interface  PolicyOverviewProjection {
        Long getId();
        LocalDate getStartDate();
        LocalDate getEndDate();
        int getCoverage();
        double getPremium();
        ObjectOfInsurance getObjectOfInsurance();
    }


