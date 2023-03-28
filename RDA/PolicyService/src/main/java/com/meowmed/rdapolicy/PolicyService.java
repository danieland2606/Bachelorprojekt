package com.meowmed.rdapolicy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;

public class PolicyService {
    
	//private static final Logger log = LoggerFactory.getLogger(PolicyService.class);

    public MappingJacksonValue getPolicyList(Long c_id, String fields, PolicyRepository pRepository) {
		MappingJacksonValue wrapper = new MappingJacksonValue(pRepository.findByCid(c_id));
		
		List<String> policyList = new ArrayList<String>();
		policyList.addAll(Arrays.asList(fields.split(",")));
		List<String> ooIList = new ArrayList<String>();

		boolean containsOoI = false;
		for (String result : policyList) {
			if(result.contains("objectOfInsurance.")){
				ooIList.add(result.substring(18));
				policyList.remove(result);
				containsOoI = true;
				//if(!policyList.contains("objectOfInsurance")){
				//	policyList.add("objectOfInsurance");
				//}
			}
		}
		if(containsOoI) policyList.add("objectOfInsurance");
		
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(policyList)))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(ooIList)))
		.setFailOnUnknownId(false));
		return wrapper;
	}

    public MappingJacksonValue getPolicy(Long c_id, Long p_id, PolicyRepository pRepository){
		MappingJacksonValue wrapper = new MappingJacksonValue(pRepository.findById(p_id));
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.serializeAllExcept("id", "c_id"))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.serializeAll())
		.setFailOnUnknownId(false));
		
		return wrapper;
	}

    public MappingJacksonValue postPolicy(Long c_id, PolicyRequest pRequest, PolicyRepository pRepository, ObjectOfInsuranceRepository oRepository){
		oRepository.save(pRequest.getObjectOfInsurance());
		PriceCalculationEntity tempCalc = new PriceCalculationEntity(12150, pRequest.getCoverage(), pRequest.getObjectOfInsurance().getRace(), 
				pRequest.getObjectOfInsurance().getColor(), pRequest.getObjectOfInsurance().getAge(), pRequest.getObjectOfInsurance().isCastrated(), 
				pRequest.getObjectOfInsurance().getPersonality(), pRequest.getObjectOfInsurance().getEnviroment(), pRequest.getObjectOfInsurance().getWeight());
		PolicyEntity policy = new PolicyEntity(c_id, pRequest.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), getPolicyPrice(tempCalc), pRequest.getObjectOfInsurance());
		MappingJacksonValue wrapper = new MappingJacksonValue(pRepository.save(policy));
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));
		return wrapper;
	}

    public double getPolicyPrice(PriceCalculationEntity body){
		double grundpreis = 0;
		double endbetrag = 0;
		switch(body.getColor()){
			case "schwarz":
				grundpreis = 0.02*body.getCoverage();
				break;
			default:
				grundpreis = 0.015*body.getCoverage();
		}
		endbetrag = grundpreis;
		endbetrag += Math.abs((body.getWeight()-4)*5);
		//krankheitsberechnungsmagie
		endbetrag += 0.1*grundpreis;
		if(ChronoUnit.YEARS.between(body.getAge(), LocalDate.now())>4){
			endbetrag += 0.2*grundpreis;
		} else if (ChronoUnit.YEARS.between(body.getAge(), LocalDate.now())<=2){
			endbetrag+=5;
		};
		if(body.isCastrated()) endbetrag+=5;
		if(body.getPostalCode()<20000) endbetrag+= 0.05*grundpreis;
		endbetrag = ((int)(endbetrag*100))/100;
		return endbetrag;
	}

	public Map<String,Double> getPolicyPriceRequest(PriceCalculationEntity body){
		return Collections.singletonMap("premium", getPolicyPrice(body));
	}
}
