package com.meowmed.rdapolicy;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdapolicy.exceptions.CatNotFoundException;
import com.meowmed.rdapolicy.exceptions.CustomerNotFoundException;
import com.meowmed.rdapolicy.exceptions.MailSendException;
import com.meowmed.rdapolicy.exceptions.PolicyNotAllowed;
import com.meowmed.rdapolicy.exceptions.PolicyNotFoundException;

import com.meowmed.rdapolicy.module.Policysetup;
import com.meowmed.rdapolicy.persistence.CatRepository;
import com.meowmed.rdapolicy.persistence.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.persistence.PolicyRepository;
import com.meowmed.rdapolicy.persistence.entity.BillingPolicyEntity;
import com.meowmed.rdapolicy.persistence.entity.CatEntity;
import com.meowmed.rdapolicy.persistence.entity.CustomerRequest;
import com.meowmed.rdapolicy.persistence.entity.MailPolicyEntity;
import com.meowmed.rdapolicy.persistence.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.persistence.entity.PolicyEntity;
import com.meowmed.rdapolicy.persistence.entity.PolicyRequest;
import com.meowmed.rdapolicy.persistence.entity.PriceCalculationEntity;

/**
 * Diese Klasse ist die Service-Klasse des REST-Controllers 
 * 
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis des Git-Repos
 * @author Alexander Hampel, Mozamil Ahmadzaei
 * 
 */
@Service
public class PolicyService {
	    
	//Variable, initialisiert durch application.properties
	@Value("${docker.notificationurl}")
	private String notificationUrl;

	//Variable, initialisiert durch application.properties
	@Value("${docker.customerurl}")
	private String customerUrl;

	//Variable, initialisiert durch application.properties
	@Value("${docker.billingurl}")
	private String billingurl;
	
	@Value("${docker.debugmode}")
	private boolean debugmode;

	private final PolicyRepository pRepository;
	private final ObjectOfInsuranceRepository oRepository;
	private final CatRepository cRepository;

	@Autowired
	public PolicyService(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository, CatRepository catRepository) {
		this.pRepository = policyRepository;
		this.oRepository = objectOfInsuranceRepository;
		this.cRepository = catRepository;
		//Policysetup.setUp(policyRepository, objectOfInsuranceRepository);
		Policysetup.catSetUp(catRepository);
	}

	/**
	 * Diese Methode nimmt die Anfrage des REST-Controllers für die PolicyListe an und gibt diese gefiltert zurück.
	 * @param c_id ID des Customers dessen Policys angefragt werden. 
	 * @param fields Eine Liste an Komma-separierten an benötigten Feldern (z.B. startDate,endDate,coverage,objectOfInsurance.name)
	 * @return Zurück kommt eine gefilterte Liste an PolicyEntitys, die ähnlich dem Beispiel aussieht:
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception PolicyNotFoundException Für den Benutzer existierern keine Verträge
	 */
    public MappingJacksonValue getPolicyList(Long c_id, String fields) throws NestedRuntimeException{
		if(debugmode) System.out.println("getPolicyList: String:" + fields);
		//Holen der benötigten Objekte
		List<PolicyEntity> policyList = pRepository.findByCid(c_id);
		if(policyList.isEmpty()) throw new PolicyNotFoundException("Für diesen Kunden gibt es keine Verträge");
		
		// Filtern der fields in 2 Arrays
		List<String> policyArgList = new ArrayList<String>();
		policyArgList.addAll(Arrays.asList(fields.split(",")));
		List<String> ooIArgList = new ArrayList<String>();
		List<String> removeList = new ArrayList<String>();
		boolean containsOoI = false;
		for (String result : policyArgList) {
			if(result.contains("objectOfInsurance.")){
				ooIArgList.add(result.substring(18));
				removeList.add(result);
				containsOoI = true;
			} else if (result.contains("objectOfInsurance")){
				ooIArgList.add("name");
				ooIArgList.add("race");
				ooIArgList.add("color");
				ooIArgList.add("dateOfBirth");
				ooIArgList.add("castrated");
				ooIArgList.add("environment");
				ooIArgList.add("weight");
			}
		}
		policyList.removeAll(removeList);
		if(containsOoI) policyArgList.add("objectOfInsurance");
		policyArgList.add("id");
		if(debugmode) System.out.println("getPolicyList: policyArgList: " + policyArgList + " ooIArgList: " + ooIArgList + " policyList: " + policyList);

		// Verpacken der Ausgabeliste + Anwenden von Filtern auf derer
		MappingJacksonValue wrapper = new MappingJacksonValue(policyList);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(policyArgList)))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(ooIArgList)))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("getPolicyList: wrapper:" + wrapper);
		return wrapper;
	}

	/**
	 * Diese Methode nimmt die Anfrage des RESTControllers an eine Policy von einem Kunden an und gibt diese gefiltert zurück
	 * @param c_id ID des Customers dessen Policys angefragt werden.
	 * @param p_id ID der Policy des Kunden.
	 * @return Zurückkommt ein PolicyEntity, bei der die Policy- und Customer-ID herausgefiltert wird
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception IllegalArgumentException Wenn einer der Argumente null ist
	 * @exception PolicyNotFoundException Wenn dieser Vertrag nicht existiert
	 * @exception CustomerNotFoundException Wenn die Customer-ID nicht mit der aus Policy übereinstimmen
	 */
    public MappingJacksonValue getPolicy(Long c_id, Long p_id) throws NestedRuntimeException {
		if(debugmode) System.out.println("getPolicy: c_id: " + c_id + " p_id: " + p_id);
		//Check, ob Kunde und Policy existieren und holen dieser Objekte
		if(c_id == null || p_id == null) throw new IllegalArgumentException();

		Optional<PolicyEntity> policy = pRepository.findById(p_id);
		if(policy.isEmpty()) throw new PolicyNotFoundException("Policy wurde nicht gefunden");
		if(policy.get().getCid() != c_id) throw new CustomerNotFoundException("Der angegebene Customer gehört nicht zur angegebenen Policy");
		if(debugmode) System.out.println("getPolicy: policy: " + policy);
		
		// Verpacken der Ausgabe + Anwenden von Filtern auf derer
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.serializeAllExcept("id", "c_id"))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("getPolicy: wrapper: " + wrapper.getSerializationView());
		return wrapper;
	}

	/**
	 * Diese Methode speichert ein PolicyEntity. PostalCode vom Customer wird beim CustomerService angefragt.
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception CustomerNotFoundException Wenn unter der angegebenen CustomerID kein Customer zu finden ist
	 * @exception IllegalArgumentException Nested aus dem getCustomerRequest
	 * @exception WebClientResponseException Nested aus dem getCustomerRequest
	 * @exception PolicyNotAllowed Falls das erstellen einer Policy nicht erlaubt ist - Nested aus getPolicyPrice
	 * @exception CatNotFoundException Die angegebene Katzenrasse existiert nicht in der Datenbank - Nested aus getPolicyPrice
	 * @exception RestClientException Nested aus dem sendMail
	 */

    public MappingJacksonValue postPolicy(Long c_id, PolicyRequest pRequest) throws NestedRuntimeException{
		if(debugmode) System.out.println("postPolicy: c_id: " + c_id + " pRequest: " + pRequest);
		// Holen, des Customers, für den Mail Versand
		CustomerRequest customer = getCustomerRequest(c_id);
		if(debugmode) System.out.println("postPolicy: customer: " + customer);

		// Erzeugen von Objekten für den Policy und speichern derer
		PriceCalculationEntity tempCalc = new PriceCalculationEntity(c_id, pRequest);
		PolicyEntity policy = new PolicyEntity(c_id, pRequest.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), getPolicyPrice(tempCalc), pRequest.getObjectOfInsurance(), pRequest.getDueDate());
		policy.setDueDate(pRequest.getDueDate());
		oRepository.save(pRequest.getObjectOfInsurance());
		policy = pRepository.save(policy);
		if(debugmode) System.out.println("postPolicy: policy: " + policy + " tempCalc: " + tempCalc);

		//Versenden von Mails
		MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
		ResponseEntity<String> response = sendMail("policynotification", mail);
		//if (response.getStatusCode() != HttpStatus.OK) throw new MailSendException();
		if(debugmode) System.out.println("postPolicy: mail: " + mail + " response: " + response);
		
		// Vorbereitung für BillingService
		BillingPolicyEntity bill = new BillingPolicyEntity(policy.getDueDate(), policy.getPremium(), customer.getBankDetails(), policy.getCid(), customer.getFirstName(), customer.getLastName(), policy.getId(), policy.getStartDate(), "Vertragsabschluss");
		sendBill(bill);
		if(debugmode) System.out.println("postPolicy: bill: " + bill);

		// Verpacken der Ausgabe + Anwenden von Filtern auf derer
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("postPolicy: wrapper: " + wrapper);
		return wrapper;
	}
	/**
	 * Diese Methode speichert ein PolicyEntity. PostalCode vom Customer wird beim CustomerService angefragt.
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception IllegalArgumentException Nested aus getCustomerRequest
	 * @exception WebClientResponseException Nested aus getCustomerRequest
	 * @exception CustomerNotFoundException Nested aus getCustomerRequest
	 * @exception CatNotFoundException Nested aus getPolicyPrice
	 * @exception MailSendException Falls die Mail nicht erfolgreich versandt wurde
	 * @exception PolicyNotAllowed Falls aus der Berechnung ergibt, das der Policy nicht erlaubt ist
	 * @exception PolicyNotFoundException Falls keine Policy vorhanden ist, auf der p_id und c_id Kombination
	 * @exception RestClientException Nested aus sendMail
	 */
    public MappingJacksonValue updatePolicy(Long c_id, Long p_id, PolicyRequest pRequest) throws NestedRuntimeException{
		if(debugmode) System.out.println("updatePolicy: c_id: " + c_id + " p_id: " + p_id + " pRequest: " + pRequest);

		//Holen des Customers und der momentanen Policy anhand der Argumente
		CustomerRequest customer = getCustomerRequest(c_id);

		Optional<PolicyEntity> oldPolicy = pRepository.findById(p_id);
		if(oldPolicy.isEmpty()) throw new PolicyNotFoundException("Policy wurde nicht gefunden");
		PolicyEntity currentPolicy = oldPolicy.get();
		if (!currentPolicy.isActive()) throw new IllegalArgumentException("Policy ist schon gekündigt");
		double oldPremium = currentPolicy.getPremium();

		//Validierung der momentanen Katze
		ObjectOfInsuranceEntity currentOoBEntity = currentPolicy.getObjectOfInsurance();
		if(!currentOoBEntity.equals(pRequest.getObjectOfInsurance())){
			currentOoBEntity.setCastrated(pRequest.getObjectOfInsurance().isCastrated());
			currentOoBEntity.setPersonality(pRequest.getObjectOfInsurance().getPersonality());
			currentOoBEntity.setEnvironment(pRequest.getObjectOfInsurance().getEnvironment());
			currentOoBEntity.setWeight(pRequest.getObjectOfInsurance().getWeight());
		}
		if(debugmode) System.out.println("updatePolicy: customer: " + customer + " currentPolicy: " + currentPolicy + " pRequest: " + pRequest);

		// Erzeugen und ersetzen der Policy
		PolicyRequest newRequest = new PolicyRequest(currentPolicy.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), pRequest.getDueDate(), currentOoBEntity);
		PriceCalculationEntity tempCalc = new PriceCalculationEntity(c_id, newRequest);
		double policyPrice = 0;
		try {
			policyPrice = getPolicyPrice(tempCalc);
		} catch (PolicyNotAllowed e) {
			deletePolicy(c_id, currentPolicy.getId());
			MailPolicyEntity mail = new MailPolicyEntity(currentPolicy, customer);
			ResponseEntity<String> response = sendMail("policydeletenotification", mail);
			if (response.getStatusCode() != HttpStatus.OK) throw new MailSendException("Mail konnte nicht versendet werden");
			if(debugmode) System.out.println("updatePolicy: mail: " + mail + " response: " + response);
			throw new PolicyNotAllowed("Policy ist nicht erlaubt");
		}

		//Hier ist die Erstellung

		PolicyEntity newPolicy = new PolicyEntity(currentPolicy.getCid(), currentPolicy.getStartDate(),currentPolicy.getEndDate(), pRequest.getCoverage(), policyPrice, currentOoBEntity, currentPolicy.getDueDate());
		newPolicy.setId(currentPolicy.getId());
		newPolicy.setDueDate(currentPolicy.getDueDate());
		newPolicy.setActive(currentPolicy.isActive());


		oRepository.save(currentOoBEntity);
		PolicyEntity policy = pRepository.save(newPolicy);
		if(debugmode) System.out.println("updatePolicy: tempCalc: " + tempCalc + " policy: " + policy);

		// Versand der Mail
		//if(!currentOoBEntity.equals(pRequest.getObjectOfInsurance()) || !currentPolicy.equals(newPolicy)){
			MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
			ResponseEntity<String> response = sendMail("policychangenotification", mail);
			if (response.getStatusCode() != HttpStatus.OK) throw new MailSendException("Mail konnte nicht versendet werden");
			if(debugmode) System.out.println("updatePolicy: mail: " + mail + " response: " + response);
		//}
		// Vorbereitung für BillingService
		if(debugmode) System.out.println("updatePolicy: newPremium: " + newPolicy.getPremium() + " oldPremium: " + currentPolicy.getPremium());
		if(newPolicy.getPremium() != oldPremium){
			BillingPolicyEntity bill = new BillingPolicyEntity(LocalDate.now(), newPolicy.getPremium()-oldPremium, customer.getBankDetails(), policy.getCid(), customer.getFirstName(), customer.getLastName(), policy.getId(), policy.getStartDate(), "Vertragsänderung");
			sendBill(bill);
			if(debugmode) System.out.println("updatePolicy: bill: " + bill);
		}
		

		// Verpacken und Filtern von der Ausgabe
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("postPolicy: wrapper: " + wrapper.getSerializationView());
		return wrapper;
	}

	/**
	 * Hier wird der Preis berechnet für monatlichen Kosten
	 * Die Berechnung kommt aus der Katzenpreisberechnungstabelle von CapGemini, die im Git liegt
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Zurück kommt ein Wert als double für die monatlichen Kosten
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception WebClientResponseException Nested aus getCustomerRequest
	 * @exception CustomerNotFoundException Nested aus getCustomerRequest
 	 * @exception PolicyNotAllowed Falls das erstellen einer Policy nicht erlaubt ist
	 * @exception CatNotFoundException Die angegebene Katzenrasse existiert nicht in der Datenbank
	 */
    public double getPolicyPrice(PriceCalculationEntity body) throws NestedRuntimeException{
		CustomerRequest customer = getCustomerRequest(body.getCustomerId());
		if(debugmode) System.out.println("getPolicyPrice: body: " + body + " customer: " + customer);
		
		// Prüfen, ob Werte sinnvoll sind
		if(body.getPolicy().getCoverage()<=0) throw new IllegalArgumentException("Coverage ist kleiner 0.");
		if(body.getPolicy().getEndDate().isBefore(body.getPolicy().getStartDate())) throw new IllegalArgumentException("EndDate is before StartDate!");
		if(LocalDate.now().isBefore(body.getPolicy().getObjectOfInsurance().getDateOfBirth())) throw new IllegalArgumentException("Katzengeburt ist nach dem Vertragsanfang");
		if(body.getPolicy().getObjectOfInsurance().getWeight()<=0) throw new IllegalArgumentException("Gewicht ist negativ/nicht vorhanden");


		// Prüfen, ob die Bedingungen für einen Vertrag erfüllt sind
		boolean policyNotAllowed = body.getPolicy().getObjectOfInsurance().getPersonality().contains("sehr verspielt") 
			|| ChronoUnit.YEARS.between(customer.getDateOfBirth() , LocalDate.now())< 18 
			|| customer.getEmploymentStatus().equalsIgnoreCase("arbeitslos")
			;
		if (policyNotAllowed) throw new PolicyNotAllowed("Policy ist nicht erlaubt");
		CatEntity cat = cRepository.findByRace(body.getPolicy().getObjectOfInsurance().getRace());
		if (cat==null) throw new CatNotFoundException("Cat wurde nicht gefunden");
		// Auskommentiert, da Farbe sehr subjektiv sind
		//if(!Arrays.asList(cat.getPossibleColors()).contains(body.getPolicy().getObjectOfInsurance().getColor())) throw new CatNotFoundException("Die Katze darf nicht diese Farbe haben");
		if(debugmode) System.out.println("getPolicyPrice: cat: " + cat);


		double baseprice = 0;
		double totalprice = 0;
		double debugprice = 0;

		// Die 0,15% der Jahresdeckung/Coverage dient als Startwert 
		// Ist die Katze Schwarz, ist die Versicherung teurer (0,2%)
		switch(body.getPolicy().getObjectOfInsurance().getColor()){
			case "schwarz":
				baseprice = 0.002*body.getPolicy().getCoverage();
				break;
			default:
				baseprice = 0.0015*body.getPolicy().getCoverage();
		}
		//  Der aktuelle Preis startet als baseprice. Später wird der Preis um Prozentteile des baseprice erhöht
		totalprice = baseprice;
		debugprice = baseprice;

		if(debugmode) System.out.println("getPolicyPrice: Nach Grundpreisberechnung: totalprice: " + totalprice + " baseprice: " + baseprice + " Differenz: " + debugprice);
		debugprice = totalprice;


		// Erhöhe die Kosten um 5€ für jedes Kilo abweichung vom Durchschnittsgewichtes der Rasse
		if(body.getPolicy().getObjectOfInsurance().getWeight() < cat.getLowerAverageWeight()){
			totalprice += Math.abs((cat.getLowerAverageWeight()-body.getPolicy().getObjectOfInsurance().getWeight())*5);
		} else if (body.getPolicy().getObjectOfInsurance().getWeight() > cat.getUpperAverageAge()){
			totalprice += Math.abs((cat.getUpperAverageWeight()-body.getPolicy().getObjectOfInsurance().getWeight())*5);
		}
		if(debugmode) System.out.println("getPolicyPrice: Nach Gewichtberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Erhöhe die Kosten des basepriceses um den Wert der Krankheitsanfälligkeitsskala (Skala von 1 bis 10)
		totalprice += cat.getIllnessFactor();

		if(debugmode) System.out.println("getPolicyPrice: Nach Illnessberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Ist es eine Draußenkatze, steigere den Preis um 1% des Grundwertes
		if(body.getPolicy().getObjectOfInsurance().getEnvironment().equalsIgnoreCase("draussen")) totalprice += 0.1*baseprice;

		if(debugmode) System.out.println("getPolicyPrice: Nach Environmentberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Ist die Katze (zum Zeitpunkt des Vertragsschlusses) im oberen Quartil des Durchschnittsalterintervals, werden 20% des Grundpreises draufgeschlagen. 
		// -10% vom Grundwert, wenn das Alter der Katze (zum Zeitpunkt des Vertragsschlusses) <=2 ist. 
		double upperQuartil  = (cat.getUpperAverageAge()-cat.getLowerAverageAge())*0.75+cat.getLowerAverageAge();
		if(ChronoUnit.YEARS.between(body.getPolicy().getObjectOfInsurance().getDateOfBirth(), LocalDate.now())>(upperQuartil)){
			totalprice += 0.2*baseprice;
		} else if (ChronoUnit.YEARS.between(body.getPolicy().getObjectOfInsurance().getDateOfBirth(), LocalDate.now())<=2){
			totalprice-=(baseprice*0.1);
		};
		if(debugmode) System.out.println("getPolicyPrice: Nach Katzenalterberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Ist die Katze unkastriert, erhöhe den Preis um 5€
		if(!body.getPolicy().getObjectOfInsurance().isCastrated()) totalprice+=5;
		if(debugmode) System.out.println("getPolicyPrice: Nach Kastrierungsberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;
		// Hat der Besitzer eine PLZ die mit 0 oder 1 startet, erhöhe den Preis um 5% des basepricees
		if(Integer.parseInt(customer.getAddress().getPostalCode())<20000) totalprice+= 0.05*baseprice;
		if(debugmode) System.out.println("getPolicyPrice: Nach Postleitzahlberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Hat der Besitzer noch einen Hund, muss 30% des basepricees noch auf den Betrag drauf gerechnet werden
		if(customer.isdogOwner()) totalprice += 0.3*baseprice;
		if(debugmode) System.out.println("getPolicyPrice: Nach Hundebesitzerberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Auf 2 Nachkommastellen runden
		totalprice = Math.round(totalprice*100.0)/100.0;
		if(totalprice<=0) throw new RestClientException("Totalprice ist negativ???"); // Hiermal RestClientException missbraucht, InvalidValueException wäre sinnvoller, bin aber zu faul grade :D
		if(debugmode) System.out.println("getPolicyPrice: totalprice: " + totalprice);
		return totalprice;
	}

	/**
	 * Diese Methode wird für Update anstoße von Verträgen von einem Customer genutzt
	 * @param c_id CustomerID
	 * @return Anzahl der geprüften Verträge
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception IllegalArgumentException Falls der übergebene Wert null ist
	 * @exception WebClientResponseException Nested aus updatePolicy
	 * @exception CustomerNotFoundException Nested aus updatePolicy
	 * @exception CatNotFoundException Nested aus updatePolicy
	 * @exception MailSendException Nested aus updatePolicy
	 * @exception PolicyNotAllowed Nested aus updatePolicy
	 * @exception PolicyNotFoundException Nested aus updatePolicy
	 * @exception RestClientException Nested aus updatePolicy
	 */
	public int changePolicyForUser(long c_id) throws NestedRuntimeException{
		if (debugmode)
			System.out.println("deletePolicyForUser: c_id: " + c_id);

		// Das Kunden Objekt wird hier nicht benötigt
		//CustomerRequest customer = getCustomerRequest(c_id);
		//if (customer == null)
		//	throw new CustomerNotFoundException("Customer wurde nicht gefunden");
		List<PolicyEntity> policies = pRepository.findByCid(c_id);
		
		// Wird nicht unbedingt benötigt, da wir mit der Vorschleife sowieso auf empty prüfen
		//if (policies.isEmpty())
		//	throw new PolicyNotFoundException("Policy wurde nicht gefunden");
		
		for (PolicyEntity policy : policies) {
			if (policy.isActive()) {
				PolicyRequest pRequest = new PolicyRequest();
				pRequest.setStartDate(policy.getStartDate());
				pRequest.setEndDate(policy.getEndDate());
				pRequest.setCoverage(policy.getCoverage());
				pRequest.setObjectOfInsurance(policy.getObjectOfInsurance());
				try {
					updatePolicy(c_id, policy.getId(), pRequest);
				} catch (PolicyNotAllowed e) {
					System.out.println(e.getMessage());
				}
			}
		}
		if (debugmode) System.out.println("deletePolicyForUser: policies: " + policies);
		//if (debugmode) System.out.println("deletePolicyForUser: customer: " + customer + " policies: " + policies);
		return policies.size();
	}

	/**
	 * Diese Methode löscht/deaktiviert einen Vertrag
	 * @param c_id CustomerID
	 * @param p_id VertragsID
	 * @exception IllegalArgumentException Falls einer der übergebenen Werte null ist
	 * @exception PolicyNotFoundException Falls es keinen Vertrag zu der Vertragsnummer gibt
	 * @exception CustomerNotFoundException Falls der Vertrag nicht zum Customer gehört
	 */
	public void deletePolicy(long c_id, long p_id) throws IllegalArgumentException, PolicyNotFoundException, CustomerNotFoundException{
		Optional<PolicyEntity> policy = pRepository.findById(p_id);
		if (policy.isEmpty()) {
			throw new PolicyNotFoundException("Policy wurde nicht gefunden");
		}
		if(policy.get().getCid() != c_id) throw new CustomerNotFoundException("Der angegebene Customer gehört nicht zur angegebenen Policy");

		policy.get().setActive(false);
		pRepository.save(policy.get());
	}

	/**
	 * Diese Methode sendet eine Anfrage an den Mail
	 * @param mailUrl Das ist der Suffix, anhand derer entschieden wird, welches template benutzt wird.
	 * @param mail Hier sind die Informationen drin, die dem Notifikationservice gegeben werden
	 * @return Die Antwort vom Notifikationservice
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception RestClientException Falls der Post-Aufruf zum Notifikationservice fehlschlägt
	 */
	private ResponseEntity<String> sendMail(String mailUrl, MailPolicyEntity mail) throws NestedRuntimeException{
		if(debugmode) System.out.println("sendMail: mailUrl: " + mailUrl + " mail: " + mail);
		String url = "http://" + notificationUrl + ":8080/"+ mailUrl;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, mail, String.class);
		if(debugmode) System.out.println("sendMail: url: " + url + " response: " + response);
		return response;
		/* 
		// Alter Versuch mit Webclient, der nichts gesendet hat.....
		Mono<ResponseEntity<String>> result = WebClient.create().post().uri("http://notification:8080/policynotification")
											//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
											.contentType(MediaType.APPLICATION_JSON)
											.bodyValue(mail)
											.retrieve()
											.toEntity(String.class);
		System.out.println(result.cast(ResponseEntity.class).toString());
		System.out.println(mail);
		*/
	}

	/**
	 * Diese Methode fragt den Customer beim Customer-Service ab
	 * @param c_id Customer-ID, des anzufragenden Customers
	 * @return CustomerRequest-Objekt, der die meisten Daten des Kunden enthält
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception IllegalArgumentException Wenn die Customer-ID null ist
	 * @exception WebClientResponseException Wenn ein Status-Code >400, außer 404, zurück gegeben wird
	 * @exception CustomerNotFoundException Wenn StatusCode 404 zurück kommt und kein Customer gefunden wurde
	 */
	public CustomerRequest getCustomerRequest(Long c_id) throws NestedRuntimeException{
		if(c_id == null) throw new IllegalArgumentException();
		if(debugmode) System.out.println("getCustomerRequest: c_id: " + c_id);
		String customerURL = "http://" + customerUrl + ":8080/customer/{c_id}";
		if(debugmode) System.out.println("getCustomerRequest: customerURL: " + customerURL);
		WebClient customerClient = WebClient.create();
		WebClient.ResponseSpec responseSpec = customerClient.get().uri(customerURL,c_id).retrieve();
		CustomerRequest customer = responseSpec
			.onStatus(RetStatus -> HttpStatus.NOT_FOUND.equals(RetStatus), ex -> {throw new CustomerNotFoundException("Es wurden keine Customer gefunden");})
			//.onStatus(RetStatus -> HttpStatus.NOT_FOUND.equals(Ret Status), ex -> Mono.empty())
			.bodyToMono(CustomerRequest.class)
			.block();
		if(customer == null) throw new CustomerNotFoundException("Customer wurde nicht gefunden");
		if(debugmode) System.out.println("getCustomerRequest: customer: " + customer);
		return customer;
	}

	/**
	 * Diese Methode sendet eine Anfrage an den BillingService
	 * @param bill Hier sind die Informationen drin, die dem BillingService gegeben werden
	 * @exception NestedRuntimeException Für alle unten aufgeführten Exceptions
	 * @exception RestClientException Falls der Post-Aufruf zum Notifikationservice fehlschlägt
	 */
	private void sendBill(BillingPolicyEntity bill) throws NestedRuntimeException{
		if(debugmode) System.out.println("sendBill: bill: " + bill);
		///customer/{id}/policy/{id}/invoice
		String url = "http://" + billingurl + ":8080/customer/" + bill.getCid() + "/policy/" + bill.getPid() + "/invoice";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(url, bill);
		//ResponseEntity<String> response = restTemplate.put(url, mail);
		//ResponseEntity<String> response = restTemplate.postForEntity(url, mail, String.class);
		//if(debugmode) System.out.println("sendMail: url: " + url + " response: " + response);
		//return response;
	}
}
