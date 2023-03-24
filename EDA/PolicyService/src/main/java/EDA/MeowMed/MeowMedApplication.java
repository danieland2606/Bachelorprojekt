package EDA.MeowMed;

import EDA.MeowMed.View.*;
import EDA.MeowMed.View.ObjectOfInsuranceOverview;
import EDA.MeowMed.View.PolicyOverview;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/customer/{c_id}/policy")
public class MeowMedApplication {

	private static HashMap<PolicyView, Long> data = new HashMap<>();

	private static void initData() {
		PolicyView policy1 = new PolicyView(1, LocalDate.of(1990, 1, 1), LocalDate.of(2030, 12, 31), 50000, 75, new ObjectOfInsuranceView("Tomato", "Bengal", "Braun", LocalDate.of(2015,7,22), true, "anhänglich", "drinnen", 4));
		PolicyView policy2 = new PolicyView(2, LocalDate.of(1992,11,1), LocalDate.of(2024,11,1),10000, 60, new ObjectOfInsuranceView("Perry", "Bengal", "Braun", LocalDate.of(2015,7,22), true, "anhänglich", "drinnen", 4));
		data.put(policy1, (long)1);
		data.put(policy2, (long)1);
	}

	public static void main(String[] args) {
		SpringApplication.run(MeowMedApplication.class, args);
		initData();
	}

	@GetMapping
	public List<PolicyOverview> getOverviewOfPolicy(@PathVariable long c_id, @RequestParam(required = false) String objectOfInsuranceName,
													@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
													@RequestParam(required = false) Integer coverage) {
		ArrayList<PolicyOverview> listOfPolicies = new ArrayList<>();
		for (PolicyView p : data.keySet()) {
			if (data.get(p) == c_id &&
				(objectOfInsuranceName == null || p.getObjectOfInsurance().getName().equals(objectOfInsuranceName)) &&
				(startDate == null || p.getStartDate().equals(LocalDate.parse(startDate))) &&
				(endDate == null || p.getEndDate().equals(LocalDate.parse(endDate))) &&
				(coverage == null || p.getCoverage() == coverage)) {

				listOfPolicies.add(new PolicyOverview(p.getId(), p.getStartDate(), p.getEndDate(), p.getCoverage(), new ObjectOfInsuranceOverview(p.getObjectOfInsurance().getName())));
			}
		}
		return listOfPolicies;
	}

	@GetMapping("/{p_id}")
	public static PolicyView getPolicy(@PathVariable long c_id, @PathVariable long p_id) {
		for (PolicyView p : data.keySet()) {
			if (data.get(p) == c_id && p.getId() == p_id) {
				return p;
			}
		}
		return null; //TODO: return 404 or something like that?
	}

	@PostMapping
	public static long addPolicy(@PathVariable long c_id, @RequestBody PolicyView policy) {
		long maxID = 0;
		for (PolicyView p : data.keySet()) {
			if (maxID < p.getId()) {
				maxID = p.getId();
			}
		}
		policy.setId(++maxID);
		data.put(policy, c_id);
		return maxID; //TODO: return error when something went wrong?
	}
}
