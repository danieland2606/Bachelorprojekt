package EDA.MeowMed.Policy.View;

import java.time.LocalDate;

public interface PolicyListOverviewProjection {
    Long getId();
    LocalDate getStartDate();
    LocalDate getEndDate();
    int getCoverage();
    double getPremium();
    ObjectOfInsuranceNameOverviewProjection ObjectOfInsuranceNameOverviewProjection(int p_id);
}
