package com.meowmed.rdapolicy;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;

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

	@Bean
	CommandLineRunner commandLineRunner(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository){
		return args -> {
			LocalDate startDate = LocalDate.of(2017, 1, 15);
			LocalDate endDate1 = LocalDate.of(2099, 1, 1);
			LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
			LocalDate birthDate2 = LocalDate.of(2015, 1, 2);
			ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
			ObjectOfInsuranceEntity cat2 = new ObjectOfInsuranceEntity("Rough", "Bengal", "Schwarz", birthDate2, false, "draufgängerisch", "drinnen", 4);
			PolicyEntity policy1 = new PolicyEntity(1 , startDate, endDate1, 50000, 765,cat1);
			PolicyEntity policy2 = new PolicyEntity(1 ,startDate, endDate1, 50000, 765 ,cat2);
			objectOfInsuranceRepository.save(cat1);
			objectOfInsuranceRepository.save(cat2);
			policyRepository.save(policy1);
			policyRepository.save(policy2);			
		};
	}
}
