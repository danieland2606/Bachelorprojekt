package com.meowmed.rdapolicy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;

public class PolicyService {
    
	private static final Logger log = LoggerFactory.getLogger(PolicyService.class);

    public List<String> getPolicyList(Long c_id) {
		LocalDate startDate = LocalDate.of(2017, 1, 15);
		LocalDate endDate1 = LocalDate.of(2099, 1, 1);
		LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
		LocalDate birthDate2 = LocalDate.of(2015, 1, 2);
		ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
		ObjectOfInsuranceEntity cat2 = new ObjectOfInsuranceEntity("Rough", "Bengal", "Schwarz", birthDate2, false, "draufgängerisch", "drinnen", 4);
		PolicyEntity policy1 = new PolicyEntity(0, c_id , startDate, endDate1, 50000, 765,cat1);
		PolicyEntity policy2 = new PolicyEntity(1, c_id,startDate, endDate1, 50000, 765 ,cat2);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("ObjectOfInsuranceFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(filterProvider);
        String fString = "leer";
        String sString = "leer";
        try {
            fString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(policy1);
            sString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(policy2);
        } catch (Exception e) {
            System.out.println(sString);
        }
        //String fString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(policy1);
        //String sString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(policy2);
		return List.of(fString, sString);
	}

    public PolicyEntity getPolicy(Long c_id, Long p_id){
		LocalDate startDate = LocalDate.of(2017, 1, 15);
		LocalDate endDate1 = LocalDate.of(2099, 1, 1);
		LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
		ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
		return new PolicyEntity(p_id, c_id , startDate, endDate1, 50000, 765 ,cat1);
	}

    public int postPolicy(Long c_id, PolicyRequest pRequest){
		// sql update Datenbank
        return ThreadLocalRandom.current().nextInt(10000);
	}

    public double postPolicyPrice(PriceCalculationEntity body){
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

	//@Bean
	//public CommandLineRunner demo (PolicyRepository repo){

	//}
}
