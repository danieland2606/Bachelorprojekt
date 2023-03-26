package com.meowmed.policy;

import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meowmed.policy.entity.PolicyEntity;
import com.meowmed.policy.entity.PolicyRequest;
import com.meowmed.policy.entity.PriceCalculationEntity;

@SpringBootApplication
@RestController
public class PolicyApplication {

	PolicyService pService = new PolicyService();

	public static void main(String[] args) {
		SpringApplication.run(PolicyApplication.class, args);
	}

	@GetMapping("/customer/{c_id}/policy")
	public List<PolicyEntity> getPolicyList(@PathVariable Long c_id) {
		return pService.getPolicyList(c_id);
	}

	@GetMapping("/customer/{c_id}/policy/{p_id}")
	public PolicyEntity getPolicy(@PathVariable Long c_id, @PathVariable Long p_id){
		return pService.getPolicy(c_id, p_id);
	}

	@PostMapping("/customer/{c_id}/policy")
	public int postPolicy(@PathVariable Long c_id, @RequestBody PolicyRequest pRequest){
		return pService.postPolicy(c_id, pRequest);
	}

	@GetMapping("/policyprice")
	public double postPolicyPrice(@RequestBody PriceCalculationEntity body){
		return pService.postPolicyPrice(body);
	}
}
