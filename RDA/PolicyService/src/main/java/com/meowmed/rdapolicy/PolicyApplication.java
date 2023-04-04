package com.meowmed.rdapolicy;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;


/**
 * Diese Klasse ist die Rest-Schnittstelle. 
 * Im Prinzip leitet diese Klasse alle Anfragen zu PolicyService um.
 * 
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis des Git-Repos
 * @author Alexander Hampel, Mozamil Ahmadzaei
 */

@SpringBootApplication
public class PolicyApplication {
	/**
	 * Main-Applikation
	 * @param args Argumente beim Programmstart
	 */
	public static void main(String[] args) {
		SpringApplication.run(PolicyApplication.class, args);
	}

	/**
	 * Diese Methode ist für die Erstellung der JPA-Repository und speichern von Beispieldaten
	 * @return Speichern der Beispieldaten
	 */
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
