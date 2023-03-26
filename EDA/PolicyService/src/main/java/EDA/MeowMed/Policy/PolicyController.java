package EDA.MeowMed.Policy;

import EDA.MeowMed.Policy.View.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer/{c_id}/policy")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public List<PolicyOverview> getOverviewOfPolicy(@PathVariable long c_id, @RequestParam(required = false) String objectOfInsuranceName,
                                                    @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
                                                    @RequestParam(required = false) Integer coverage) {
        return this.policyService.getPolicyList(c_id, objectOfInsuranceName, startDate, endDate, coverage);
    }

    @GetMapping("/{p_id}")
    public PolicyView getPolicy(@PathVariable long c_id, @PathVariable long p_id) {
        return this.policyService.getPolicyById(c_id, p_id); //TODO: return 404 or something like that?
    }

    @PostMapping
    public long addPolicy(@PathVariable long c_id, @RequestBody PolicyView policy) {
        return this.policyService.addPolicy(c_id, policy); //TODO: return error when something went wrong?
    }
}
