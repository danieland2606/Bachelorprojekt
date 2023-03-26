package EDA.MeowMed.Storage;

import EDA.MeowMed.View.ObjectOfInsuranceView;
import EDA.MeowMed.View.PolicyView;

import java.time.LocalDate;
import java.util.HashMap;

public class TemporaryStorage {

    private HashMap<PolicyView, Long> data = new HashMap<>();

    public TemporaryStorage() {
        this.initData();
    }

    public HashMap<PolicyView, Long> getData() {
        return data;
    }

    private void initData() {
        PolicyView policy1 = new PolicyView(1, LocalDate.of(1990, 1, 1), LocalDate.of(2030, 12, 31), 50000, 75, new ObjectOfInsuranceView("Tomato", "Bengal", "Braun", LocalDate.of(2015,7,22), true, "anhänglich", "drinnen", 4));
        PolicyView policy2 = new PolicyView(2, LocalDate.of(1992,11,1), LocalDate.of(2024,11,1),10000, 60, new ObjectOfInsuranceView("Perry", "Bengal", "Braun", LocalDate.of(2015,7,22), true, "anhänglich", "drinnen", 4));
        data.put(policy1, (long)1);
        data.put(policy2, (long)1);
    }
}
