package com.meowmed.policy;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meowmed.policy.entity.ObjectOfInsuranceEntity;
import com.meowmed.policy.entity.PolicyEntity;
import com.meowmed.policy.entity.PriceCalculationEntity;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class PolicyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyApplication.class, args);
	}

	@GetMapping("/customer/{c_id}/policy")
	public List<PolicyEntity> getPolicyList(@PathVariable Long c_id) {
		LocalDate startDate = LocalDate.of(2017, 1, 15);
		LocalDate endDate1 = LocalDate.of(2099, 1, 1);
		LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
		LocalDate birthDate2 = LocalDate.of(2015, 1, 2);
		ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
		ObjectOfInsuranceEntity cat2 = new ObjectOfInsuranceEntity("Rough", "Bengal", "Schwarz", birthDate2, false, "draufgängerisch", "drinnen", 4);
		PolicyEntity policy1 = new PolicyEntity(0, c_id , startDate, endDate1, 50000, cat1);
		PolicyEntity policy2 = new PolicyEntity(1, c_id,startDate, endDate1, 50000, cat2);
		return List.of(policy1, policy2);
	}

	@GetMapping("/customer/{c_id}/policy/{p_id}")
	public PolicyEntity getPolicy(@PathVariable Long c_id, @PathVariable Long p_id){
		LocalDate startDate = LocalDate.of(2017, 1, 15);
		LocalDate endDate1 = LocalDate.of(2099, 1, 1);
		LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
		ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
		return new PolicyEntity(p_id, c_id , startDate, endDate1, 50000, cat1);
	}

	@PostMapping("/customer/{c_id}/policy")
	public int postPolicy(@PathVariable Long c_id){
		return ThreadLocalRandom.current().nextInt(10000);
	}

	@PutMapping("/customer/{c_id}/policy/{p_id}")
	public PolicyEntity putPolicy(@RequestBody PolicyEntity policy, @PathVariable Long c_id, @PathVariable Long p_id){
		return policy;
	}

	@PostMapping("/policyprice")
	public double postPolicyPrice(@RequestBody PriceCalculationEntity body){
		return 0.2;
	}
}
