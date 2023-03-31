package com.meowmed.rdapolicy;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;

/**
 * Diese Klasse ist die Rest-Schnittstelle. 
 * Im Prinzip leitet diese Klasse alle Anfragen zu PolicyService um.
 * 
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis des Git-Repos
 * @author Alexander Hampel, Mozamil Ahmadzaei
 * 
 */

@SpringBootApplication
@RestController
public class PolicyApplication {

	PolicyService pService = new PolicyService();
	@Autowired
	PolicyRepository pRepository;
	@Autowired
	ObjectOfInsuranceRepository oRepository;

	/**
	 * Main-Applikation
	 * @param args Argumente beim Programmstart
	 */
	public static void main(String[] args) {
		SpringApplication.run(PolicyApplication.class, args);
	}

	/**
	 * Diese Methode nimmt die GET-Anfrage für die PolicyListe an und gibt diese gefiltert zurück.
	 * @url "http://domain:port/customer/"Kunden-ID"/policy"
	 * @param c_id ID des Customers dessen Policys angefragt werden. 
	 * @param fields Eine Liste an Komma-separierten an benötigten Feldern (z.B. startDate,endDate,coverage,objectOfInsurance.name)
	 * @return Zurück kommt eine gefilterte Liste an PolicyEntitys, die ähnlich dem Beispiel aussieht:
	 *	   "policyList": [
			{
			"id": 0,
			"startDate": "1990-01-01",
			"endDate": "2030-12-31",
			"coverage": 50000,
			"objectOfInsurance": {
				"name": "Tomato"
			}
			},
			{
			"id": 1,
			"startDate": "1992-11-01",
			"endDate": "2024-11-01",
			"coverage": 10000,
			"objectOfInsurance": {
				"name": "Perry"
			}
			}
		]
	 */
	@GetMapping("/customer/{c_id}/policy")
	public MappingJacksonValue getPolicyList(@PathVariable Long c_id, @RequestParam(value = "fields") String fields) {
		return pService.getPolicyList(c_id,fields, pRepository);
	}

	/**
	 * Diese Methode nimmt die Get-Anfrage an eine Policy von einem Kunden an und gibt diese gefiltert zurück
	 * @url "http://domain:port/customer/"Kunden-ID"/policy/"Policy-ID"
	 * @param c_id ID des Customers dessen Policys angefragt werden.
	 * @param p_id ID der Policy des Kunden.
	 * @return Zurückkommt ein PolicyEntity, bei der die Policy- und Customer-ID herausgefiltert wird
	 * 		{
				"startDate": "1990-01-01",
				"endDate": "2030-12-31",
				"coverage": 50000,
				"premium": 75,
				"objectOfInsurance": {
					"name": "Tomato",
					"race": "Bengal",
					"color": "Braun",
					"dateOfBirth": "2015-07-22",
					"castrated": true,
					"personality": "anhänglich",
					"environment": "drinnen",
					"weight": 4
				}
			}
	 */
	@GetMapping("/customer/{c_id}/policy/{p_id}")
	public MappingJacksonValue getPolicy(@PathVariable Long c_id, @PathVariable Long p_id){
		return pService.getPolicy(c_id, p_id, pRepository);
	}

	/**
	 * Diese Methode speichert ein PolicyEntity.
	 * @url "http://domain:port/customer/"Kunden-ID"/policy"
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * 			{
					"startDate": "1990-01-01",
					"endDate": "2030-12-31",
					"coverage": 50000,
					"objectOfInsurance": {
						"name": "Tomato",
						"race": "Bengal",
						"color": "Braun",
						"dateOfBirth": "2015-07-22",
						"castrated": true,
						"personality": "anhänglich",
						"environment": "drinnen",
						"weight": 4
					}
				}
	 * @return Die ID der gerade erstellten Objekts
	 * 		{
			"id": 0
			}
	 * 
	 */
	@PostMapping("/customer/{c_id}/policy")
	public MappingJacksonValue postPolicy(@PathVariable Long c_id, @RequestBody PolicyRequest pRequest){
		return pService.postPolicy(c_id, pRequest, pRepository, oRepository);
	}

	/**
	 * Diese Methode gibt den Preis für den Berechnungsbutton auf der Webseite zurück
	 * @param body Die Parameter für die Berechnung als Objekt PriceCalculationEntity
	 * 	{
		"postalCode": "30855",
		"coverage": 50000,
		"race": "Bengal",
		"color": "Braun",
		"dateOfBirth": "2015-07-22",
		"castrated": true,
		"personality": "anhänglich",
		"environment": "drinnen",
		"weight": 4
		}
	 * @return Zurück kommt der Preis
	 *  {
		"premium": 75
		}
	 */
	@GetMapping("/policyprice")
	public Map<String,Double> getPolicyPrice(@RequestBody PriceCalculationEntity body){
		return pService.getPolicyPriceRequest(body);
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
