package EDA.MeowMed;

import EDA.MeowMed.Logic.*;
import EDA.MeowMed.View.*;
import EDA.MeowMed.View.PolicyOverview;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/customer/{c_id}/policy")
public class MeowMedApplication {

	private static Logic logic;

	public static void main(String[] args) {
		SpringApplication.run(MeowMedApplication.class, args);
		logic = new Logic();
	}

	@GetMapping
	public List<PolicyOverview> getOverviewOfPolicy(@PathVariable long c_id, @RequestParam(required = false) String objectOfInsuranceName,
													@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
													@RequestParam(required = false) Integer coverage) {
		return logic.getPolicyList(c_id, objectOfInsuranceName, startDate, endDate, coverage);
	}

	@GetMapping("/{p_id}")
	public static PolicyView getPolicy(@PathVariable long c_id, @PathVariable long p_id) {
		return logic.getPolicyById(c_id, p_id); //TODO: return 404 or something like that?
	}

	@PostMapping
	public static long addPolicy(@PathVariable long c_id, @RequestBody PolicyView policy) {
		return logic.addPolicy(c_id, policy); //TODO: return error when something went wrong?
	}
}
