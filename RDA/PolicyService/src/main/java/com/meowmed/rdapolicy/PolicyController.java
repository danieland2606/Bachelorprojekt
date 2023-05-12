package com.meowmed.rdapolicy;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
import com.meowmed.rdapolicy.exceptions.CatNotFoundException;
import com.meowmed.rdapolicy.exceptions.CustomerNotFoundException;
import com.meowmed.rdapolicy.exceptions.MailSendException;
import com.meowmed.rdapolicy.exceptions.PolicyNotAllowed;
import com.meowmed.rdapolicy.exceptions.PolicyNotFoundException;
import com.meowmed.rdapolicy.persistence.entity.PolicyRequest;
import com.meowmed.rdapolicy.persistence.entity.PriceCalculationEntity;

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
	 * @return Zurück kommt eine gefilterte Liste an PolicyEntitys, die ähnlich dem Beispiel aus Swagger aussieht.
	 */
	@GetMapping("/customer/{c_id}/policy")
	public ResponseEntity<MappingJacksonValue> getPolicyList(@PathVariable Long c_id, @RequestParam(value = "fields") String fields){
		try{
			return ResponseEntity.status(200).body(pService.getPolicyList(c_id,fields));
		} catch (PolicyNotFoundException e) {
			return ResponseEntity.status(204).body(new MappingJacksonValue(Collections.singletonMap("message", e.getMessage())));
			//MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", e.getMessage()));
			//return ResponseEntity.status(400).body(errWrapper);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		}
	}

	/**
	 * Diese Methode nimmt die Get-Anfrage an eine Policy von einem Kunden an und gibt diese gefiltert zurück
	 * @url "http://domain:port/customer/"Kunden-ID"/policy/"Policy-ID"
	 * @param c_id ID des Customers dessen Policys angefragt werden.
	 * @param p_id ID der Policy des Kunden.
	 * @return Zurückkommt ein PolicyEntity, bei der die Policy- und Customer-ID herausgefiltert wird
	 */
	@GetMapping("/customer/{c_id}/policy/{p_id}")
	public ResponseEntity<MappingJacksonValue> getPolicy(@PathVariable Long c_id, @PathVariable Long p_id){
		try {
			return ResponseEntity.status(200).body(pService.getPolicy(c_id,p_id));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotFoundException e){
			return ResponseEntity.status(404).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (CustomerNotFoundException e){
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		}
	}

	/**
	 * Diese Methode speichert ein PolicyEntity.
	 * @url "http://domain:port/customer/"Kunden-ID"/policy"
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 */
	@PostMapping("/customer/{c_id}/policy")
	public ResponseEntity<MappingJacksonValue> postPolicy(@PathVariable Long c_id, @RequestBody PolicyRequest pRequest){
		try {
			return ResponseEntity.status(201).body(pService.postPolicy(c_id, pRequest));
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (WebClientResponseException e){	
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotAllowed e){
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (CatNotFoundException e){
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (RestClientException e){
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		}
	}

		/**
	 * Diese Methode speichert ein PolicyEntity.
	 * @url "http://domain:port/customer/"Kunden-ID"/policy"
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 */
	@PutMapping("/customer/{c_id}/policy/{p_id}")
	public ResponseEntity<MappingJacksonValue> updatePolicy(@PathVariable("c_id") Long c_id, @PathVariable("p_id") Long p_id, @RequestBody PolicyRequest pRequest) throws JsonProcessingException{
		try {
			return ResponseEntity.status(204).body(pService.updatePolicy(c_id, p_id, pRequest));
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotAllowed e ) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotFoundException e) {
			return ResponseEntity.status(404).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (MailSendException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (WebClientResponseException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (CatNotFoundException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (RestClientException e ) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		}
	}
	/**
	 * Diese Methode gibt den Preis für den Berechnungsbutton auf der Webseite zurück
	 * @url "http://domain:port/policyprice"
	 * @param body Die Parameter für die Berechnung als Objekt PriceCalculationEntity
	 * @return Zurück kommt der Preis
	 */
	@PostMapping("/policyprice")
	public ResponseEntity<MappingJacksonValue> getPolicyPrice(@RequestBody PriceCalculationEntity details) throws JsonProcessingException{
		try {
			return ResponseEntity.status(200).body(new MappingJacksonValue(Collections.singletonMap("premium", pService.getPolicyPrice(details))));
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotAllowed e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (CatNotFoundException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (WebClientResponseException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} 
	}

	/**
	 * Diese Methode wird für Update anstoße von Verträgen von einem Customer genutzt
	 * @url "http://domain:port/customer/"Kunden-ID"/policy"
	 * @param c_id Die CustomerID
	 * @return Anzahl der veränderten Verträge
	 */

	@DeleteMapping("/customer/{c_id}/policy")
	public ResponseEntity<MappingJacksonValue> deletePolicyForUser(@PathVariable("c_id") Long c_id){
		try {
			return ResponseEntity.status(200).body(new MappingJacksonValue(Collections.singletonMap("Changed Policies", pService.deletePolicyForUser(c_id))));
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(404).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotAllowed e ) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotFoundException e) {
			return ResponseEntity.status(404).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (MailSendException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (WebClientResponseException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (CatNotFoundException e) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (RestClientException e ) {
			return ResponseEntity.status(500).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		}
	}

	/**
	 * Diese Methode deaktiviert einen Vertrag
	 * @param c_id CustomerID
	 * @param p_id VertragsID
	 * @return Anzahl der veränderten Verträge
	 */
	@DeleteMapping("customer/{c_id}/policy/{p_id}")
	public ResponseEntity<MappingJacksonValue> deletePolicy(@PathVariable("c_id") Long c_id, @PathVariable("p_id") Long p_id){
		try {
			pService.deletePolicy(c_id,p_id);
			return ResponseEntity.status(200).body(new MappingJacksonValue(Collections.singletonMap("Changed Policies", 1)));	
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (PolicyNotFoundException e) {
			return ResponseEntity.status(404).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.status(404).body(new MappingJacksonValue(Collections.singletonMap("error", e.getMessage())));
		}
	}
}