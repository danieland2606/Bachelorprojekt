package com.meowmed.rdapolicy;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyPostResponse;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;
import com.meowmed.rdapolicy.entity.PriceCalculationReturn;

@SpringBootApplication
@RestController
public class PolicyApplication {

	PolicyService pService = new PolicyService();
	PolicyRepository pRepository;
	ObjectOfInsuranceRepository oRepository;

	public static void main(String[] args) {
		SpringApplication.run(PolicyApplication.class, args);
	}

	@GetMapping("/customer/{c_id}/policy")
	public MappingJacksonValue getPolicyList(@PathVariable Long c_id, @RequestParam(value = "fields") String fields) {
		return pService.getPolicyList(c_id,fields, pRepository);
	}

	@GetMapping("/customer/{c_id}/policy/{p_id}")
	public MappingJacksonValue getPolicy(@PathVariable Long c_id, @PathVariable Long p_id){
		return pService.getPolicy(c_id, p_id, pRepository);
	}

	@PostMapping("/customer/{c_id}/policy")
	public PolicyPostResponse postPolicy(@PathVariable Long c_id, @RequestBody PolicyRequest pRequest){
		return pService.postPolicy(c_id, pRequest, pRepository, oRepository);
	}

	@GetMapping("/policyprice")
	public PriceCalculationReturn getPolicyPrice(@RequestBody PriceCalculationEntity body){
		return pService.getPolicyPriceRequest(body);
	}

	@Bean
	CommandLineRunner commandLineRunner(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository){
		pRepository = policyRepository;
		oRepository = objectOfInsuranceRepository;
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
	/*
	protected PolicyRepository getPolicyRepository(){
		return pRepository;
	}

	protected ObjectOfInsuranceRepository getObjectOfInsuranceRepository(){
		return oRepository;
	}
	*/
}
