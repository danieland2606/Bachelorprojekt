package com.meowmed.rdapolicy;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;
import com.meowmed.rdapolicy.exceptions.CustomerNotFoundException;
import com.meowmed.rdapolicy.exceptions.MailSendException;
import com.meowmed.rdapolicy.exceptions.PolicyNotFoundException;

/**
 * Diese Klasse ist der REST-Controller
 * 
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis des Git-Repos
 * @author Alexander Hampel, Mozamil Ahmadzaei
 * 
 */

@RestController
public class PolicyController {

    private final PolicyService pService;

	/**
	 * Diese Methode ist die Initialisierung des REST-Controllers
	 * @param policyService Dependency-Injektion von Spring Boot
	 */
    @Autowired
    public PolicyController(PolicyService policyService){
        this.pService = policyService;
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
	public ResponseEntity<MappingJacksonValue> getPolicyList(@PathVariable Long c_id, @RequestParam(value = "fields") String fields){
		try{
			return new ResponseEntity<MappingJacksonValue>(pService.getPolicyList(c_id,fields),HttpStatusCode.valueOf(200));
		} catch (IllegalArgumentException e){
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Something went horrible wrong"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(400));
		} catch (PolicyNotFoundException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("message", "No Policy found"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(204));
		}
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
	public ResponseEntity<MappingJacksonValue> getPolicy(@PathVariable Long c_id, @PathVariable Long p_id){
		try {
			return new ResponseEntity<MappingJacksonValue>(pService.getPolicy(c_id,p_id),HttpStatusCode.valueOf(200));
		} catch (IllegalArgumentException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Einer der Übergabewerte ist null"));
			return ResponseEntity.status(400).body(errWrapper);
		} catch (PolicyNotFoundException e){
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Policy/Customer not found"));
			return ResponseEntity.status(404).body(errWrapper);
		}
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
	public ResponseEntity<MappingJacksonValue> postPolicy(@PathVariable Long c_id, @RequestBody PolicyRequest pRequest){
		try {
			return new ResponseEntity<MappingJacksonValue>(pService.postPolicy(c_id, pRequest),HttpStatusCode.valueOf(201));
		} catch (CustomerNotFoundException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Unter der angegebenen Customer-ID wurde kein Customer gefunden"));
			return ResponseEntity.status(400).body(errWrapper);
		} catch (IllegalArgumentException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Einer der Parameter ist null"));
			return ResponseEntity.status(400).body(errWrapper);
		} catch (MailSendException e){
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Mail-Versand fehlgeschlagen, Daten sind aber gespeichert."));
			return ResponseEntity.status(500).body(errWrapper);
		} catch (WebClientException e){
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Mail-Service erzeugt unerwarteten Fehler, Daten sind aber gespeichert."));
			return ResponseEntity.status(500).body(errWrapper);
		} catch (ArithmeticException e){
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Bei der Preisberechnung ist ein Fehler entstanden"));
			return ResponseEntity.status(500).body(errWrapper);
		}
		//return pService.postPolicy(c_id, pRequest);
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
	@PutMapping("/customer/{c_id}/policy/{p_id}")
	public ResponseEntity<MappingJacksonValue> updatePolicy(@PathVariable("c_id") Long c_id, @PathVariable("p_id") Long p_id, @RequestBody PolicyRequest pRequest) throws JsonProcessingException{
		try {
			return new ResponseEntity<MappingJacksonValue>(pService.updatePolicy(c_id, p_id, pRequest),HttpStatusCode.valueOf(204));
		} catch (CustomerNotFoundException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Unter der angegebenen Customer-ID wurde kein Customer gefunden"));
			return ResponseEntity.status(400).body(errWrapper);		
		} catch (MailSendException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Mail-Versand fehlgeschlagen, Daten sind aber gespeichert."));
			return ResponseEntity.status(500).body(errWrapper);
		} catch (WebClientException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Mail-Service erzeugt unerwarteten Fehler, Daten sind aber gespeichert."));
			return ResponseEntity.status(500).body(errWrapper);
		} catch (PolicyNotFoundException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Unter der angegebenen Policy-ID wurde keine Policy gefunden"));
			return ResponseEntity.status(400).body(errWrapper);
		} catch (IllegalArgumentException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Einer der Argumente ist null"));
			return ResponseEntity.status(400).body(errWrapper);
		}
		
		//return pService.updatePolicy(c_id, p_id, pRequest);
	}
	//CustomerNotFoundException, MailSendException, PolicyNotFoundException, IllegalArgumentException
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
	@PostMapping("/policyprice")
	public ResponseEntity<MappingJacksonValue> getPolicyPrice(@RequestBody PriceCalculationEntity details) throws JsonProcessingException{
		try {
			return new ResponseEntity<MappingJacksonValue>(pService.getPolicyPriceRequest(details),HttpStatusCode.valueOf(200));
		} catch (ArithmeticException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Bei der Berechnung ist ein Fehler aufgetreten."));
			return ResponseEntity.status(500).body(errWrapper);
		} catch (CustomerNotFoundException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Unter der angegebenen Customer-ID wurde kein Customer gefunden"));
			return ResponseEntity.status(400).body(errWrapper);		
		} catch (WebClientException e) {
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Mail-Service erzeugt unerwarteten Fehler, Daten sind aber gespeichert."));
			return ResponseEntity.status(500).body(errWrapper);
		} 
		//return pService.getPolicyPriceRequest(details);
	}

	@DeleteMapping("/customer/{c_id}/policy")
	public ResponseEntity<MappingJacksonValue> deletePolicyForUser(@PathVariable("c_id") Long c_id){
		MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("Angepasste Verträge", pService.deletePolicyForUser(c_id)));
		return ResponseEntity.status(200).body(errWrapper);
	}

	@DeleteMapping("customer/{c_id}/policy/{p_id}")
	public ResponseEntity<MappingJacksonValue> deletePolicy(@PathVariable("c_id") Long c_id, @PathVariable("p_id") Long p_id){
		MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", pService.deletePolicy(c_id, p_id)));
		return ResponseEntity.status(200).body(errWrapper);
	}
	///customer/{c_id}/policy/{p_id}
}