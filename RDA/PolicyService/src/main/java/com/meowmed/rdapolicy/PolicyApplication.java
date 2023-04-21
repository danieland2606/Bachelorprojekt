package com.meowmed.rdapolicy;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.meowmed.rdapolicy.database.CatRepository;
import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.CatEntity;
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
	/*
	@Bean
	CommandLineRunner commandLineRunner(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository, CatRepository catRepository){
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
			ArrayList<CatEntity> entities = new ArrayList<>();
			entities.add(new CatEntity("siamese", 12, 15, 4, 7, 2, new String[]{"seal","blau","lilac","creme"})) ;
			entities.add(new CatEntity("perser", 12, 16, 4, 7, 3, new String[]{"weiß", "schildpatt","schwarz"}));
			entities.add(new CatEntity("bengal", 12, 16, 4, 6, 4, new String[]{"braun", "schildpatt","marmor"}));
			entities.add(new CatEntity("maine-cone", 12, 15, 5, 10, 2, new String[]{"grau","braun","weiß"}));
			entities.add(new CatEntity("sphynx", 12, 15, 4, 6, 5, new String[]{}));
			entities.add(new CatEntity("scottish Fold", 12, 15, 4, 6, 6, new String[]{}));
			entities.add(new CatEntity("british-shorthair", 12, 15, 4, 6, 0, new String[]{}));
			entities.add(new CatEntity("abyssinian", 12, 15, 3, 5, 4, new String[]{"rot", "schildpatt", "zimt"}));
			entities.add(new CatEntity("ragdoll", 12, 15, 4, 7, 3, new String[]{"blau", "seal", "lilac", "schildpatt"}));
			catRepository.saveAll(entities);
		};
	}
	*/

}
