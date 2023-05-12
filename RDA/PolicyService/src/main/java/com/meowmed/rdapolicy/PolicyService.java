package com.meowmed.rdapolicy;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdapolicy.database.CatRepository;
import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.CatEntity;
import com.meowmed.rdapolicy.entity.CustomerRequest;
import com.meowmed.rdapolicy.entity.MailPolicyEntity;
import com.meowmed.rdapolicy.entity.ObjectOfInsuranceEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;
import com.meowmed.rdapolicy.exceptions.CustomerNotFoundException;
import com.meowmed.rdapolicy.exceptions.MailSendException;
import com.meowmed.rdapolicy.exceptions.PolicyNotFoundException;

import jakarta.websocket.OnError;
import reactor.core.publisher.Mono;


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
	@Value("${docker.customerurl}")
	private String customerUrl;
	//Variable, initialisiert durch application.properties
	@Value("${docker.notificationurl}")
	private String notificationUrl;

	@Value("${docker.debugmode}")
	private boolean debugmode;
	//if(debugmode) System.out.println("getPolicyList:");


	private final PolicyRepository pRepository;
	private final ObjectOfInsuranceRepository oRepository;
	private final CatRepository cRepository;

	@Autowired
	public PolicyService(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository, CatRepository catRepository) {
		this.pRepository = policyRepository;
		this.oRepository = objectOfInsuranceRepository;
		this.cRepository = catRepository;
		setUp();
	}

    /**
	 * Diese Methode nimmt die Anfrage des REST-Controllers für die PolicyListe an und gibt diese gefiltert zurück.
	 * @param c_id ID des Customers dessen Policys angefragt werden. 
	 * @param fields Eine Liste an Komma-separierten an benötigten Feldern (z.B. startDate,endDate,coverage,objectOfInsurance.name)
	 * @return Zurück kommt eine gefilterte Liste an PolicyEntitys, die ähnlich dem Beispiel aussieht:
	 */
    public MappingJacksonValue getPolicyList(Long c_id, String fields) throws IllegalArgumentException, PolicyNotFoundException{
		if(debugmode) System.out.println("getPolicyList: String:" + fields);
		//Holen der benötigten Objekte
		List<PolicyEntity> policyList = pRepository.findByCid(c_id);
		if(policyList.isEmpty()) throw new PolicyNotFoundException();
		
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
	 */
    public MappingJacksonValue getPolicy(Long c_id, Long p_id) throws IllegalArgumentException, PolicyNotFoundException {
		if(debugmode) System.out.println("getPolicy: c_id: " + c_id + " p_id: " + p_id);
		//Check, ob Kunde und Policy existieren und holen dieser Objekte
		if(c_id == null || p_id == null) throw new IllegalArgumentException();

		Optional<PolicyEntity> policy = pRepository.findById(p_id);
		if(policy.isEmpty()) throw new PolicyNotFoundException();
		if(debugmode) System.out.println("getPolicy: policy: " + policy);
		
		// Verpacken der Ausgabe + Anwenden von Filtern auf derer
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		//System.out.println(wrapper.getValue().getClass());
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.serializeAllExcept("id", "c_id"))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("getPolicy: wrapper: " + wrapper);
		return wrapper;
		//return ResponseEntity.ok().body(wrapper);
	}

	/**
	 * Diese Methode speichert ein PolicyEntity. PostalCode vom Customer wird beim CustomerService angefragt.
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 */
    public MappingJacksonValue postPolicy(Long c_id, PolicyRequest pRequest) throws IllegalArgumentException, WebClientException, CustomerNotFoundException, MailSendException, ArithmeticException{
		if(debugmode) System.out.println("postPolicy: c_id: " + c_id + " pRequest: " + pRequest);
		// Holen, des Customers
		CustomerRequest customer = getCustomerRequest(c_id);
		if(customer == null) throw new CustomerNotFoundException();
		/*{
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "No Customer under this ID"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(400));
		}*/
		if(debugmode) System.out.println("postPolicy: customer: " + customer);

		// Erzeugen von Objekten für den Policy und speichern derer
		PriceCalculationEntity tempCalc = new PriceCalculationEntity(c_id, pRequest);
		PolicyEntity policy = new PolicyEntity(c_id, pRequest.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), getPolicyPrice(tempCalc), pRequest.getObjectOfInsurance());
		oRepository.save(pRequest.getObjectOfInsurance());
		policy = pRepository.save(policy);
		if(debugmode) System.out.println("postPolicy: policy: " + policy + " tempCalc: " + tempCalc);

		//Versenden von Mails
		MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
		ResponseEntity<String> response = sendMail("policynotification", mail);
		//if (response.getStatusCode() != HttpStatus.OK) throw new MailSendException();
		/*{
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Es gab Probleme, die Mail zu versenden, Daten wurden aber gespeichert"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(400));
		}*/
		if(debugmode) System.out.println("postPolicy: mail: " + mail + " response: " + response);

		// Verpacken der Ausgabe + Anwenden von Filtern auf derer
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("postPolicy: wrapper: " + wrapper);
		return wrapper;
		//return new ResponseEntity<MappingJacksonValue>(wrapper,HttpStatusCode.valueOf(201));
	}
	/**
	 * Diese Methode speichert ein PolicyEntity. PostalCode vom Customer wird beim CustomerService angefragt.
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 * 
	 */
    public MappingJacksonValue updatePolicy(Long c_id, Long p_id, PolicyRequest pRequest) throws WebClientException, CustomerNotFoundException, MailSendException, PolicyNotFoundException, IllegalArgumentException{
		if(debugmode) System.out.println("updatePolicy: c_id: " + c_id + " p_id: " + p_id + " pRequest: " + pRequest);

		//Holen des Customers und der momentanen Policy anhand der Argumente
		CustomerRequest customer = getCustomerRequest(c_id);
		if(customer == null) throw new CustomerNotFoundException();
		/*{
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "No Customer under this ID"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(404));
		}*/
		Optional<PolicyEntity> currentPolicy = pRepository.findById(p_id);
		if(currentPolicy.isEmpty()) throw new PolicyNotFoundException();
		/*{
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "No policy under this id"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(404));
		}*/
		//Setzen der ID der momentanen Katze
		pRequest.getObjectOfInsurance().setId(currentPolicy.get().getObjectOfInsurance().getId());
		if(debugmode) System.out.println("updatePolicy: customer: " + customer + " currentPolicy: " + currentPolicy + " pRequest: " + pRequest);

		// Erzeugen und ersetzen der Policy
		PriceCalculationEntity tempCalc = new PriceCalculationEntity(c_id, pRequest);
		PolicyEntity policy = new PolicyEntity(c_id, pRequest.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), getPolicyPrice(tempCalc), pRequest.getObjectOfInsurance());
		oRepository.save(pRequest.getObjectOfInsurance());
		policy.setId(p_id);
		policy = pRepository.save(policy);
		if(debugmode) System.out.println("updatePolicy: tempCalc: " + tempCalc + " policy: " + policy);

		// Versand der Mail
		MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
		ResponseEntity<String> response = sendMail("policychangenotification", mail);
		if (response.getStatusCode() != HttpStatus.OK) throw new MailSendException();
		/*{
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Es gab Probleme, die Mail zu versenden, Daten wurden aber gespeichert"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(400));
		}*/
		if(debugmode) System.out.println("updatePolicy: mail: " + mail + " response: " + response);

		// Verpacken und Filtern von der Ausgabe
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));
		if(debugmode) System.out.println("postPolicy: wrapper: " + wrapper);
		return wrapper;
		//return new ResponseEntity<MappingJacksonValue>(wrapper,HttpStatusCode.valueOf(201));
	}

	/**
	 * Hier wird der Preis berechnet für monatlichen Kosten
	 * Die Berechnung kommt aus der Katzenpreisberechnungstabelle von CapGemini, die im Git liegt
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Zurück kommt ein Wert als double für die monatlichen Kosten
	 */
    public double getPolicyPrice(PriceCalculationEntity body) throws WebClientException, CustomerNotFoundException, ArithmeticException{
		CustomerRequest customer = getCustomerRequest(body.getCustomerId());
		if(debugmode) System.out.println("getPolicyPrice: body: " + body + " customer: " + customer);
		
		// Prüfen, ob die Katze sehr verspielt ist, und Besitzer älter als 18 Jahre
		if (body.getPolicy().getObjectOfInsurance().getPersonality().contains("sehr verspielt") || ChronoUnit.YEARS.between(customer.getDateOfBirth(), LocalDate.now())<18) return 0;
		CatEntity cat = cRepository.findByRace(body.getPolicy().getObjectOfInsurance().getRace());
		if (cat==null) return 0;
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
		// TODO: hasDog auf dogOwner ändern
		if(customer.isdogOwner()) totalprice += 0.3*baseprice;
		if(debugmode) System.out.println("getPolicyPrice: Nach Hundebesitzerberechnung: totalprice: " + totalprice + " Differenz: " + (totalprice - debugprice));
		debugprice = totalprice;

		// Auf 2 Nachkommastellen runden
		totalprice = ((int)(totalprice*100))/100;
		if(debugmode) System.out.println("getPolicyPrice: totalprice: " + totalprice);

		return totalprice;
	}

	/**
	 * Die Wert für die monatliche Kosten werden in ein JSONObjekt umgewandelt
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Ein zu JSON umwandelbarer Wert
	*/
	public MappingJacksonValue getPolicyPriceRequest(PriceCalculationEntity details) throws WebClientException, CustomerNotFoundException, ArithmeticException{
		if(debugmode) System.out.println("getPolicyPriceRequest: details: " + details);
		MappingJacksonValue wrapper = new MappingJacksonValue(Collections.singletonMap("premium", getPolicyPrice(details)));
		return wrapper;
		/*try{
			MappingJacksonValue wrapper = new MappingJacksonValue(Collections.singletonMap("premium", getPolicyPrice(details)));
			return new ResponseEntity<MappingJacksonValue>(wrapper,HttpStatusCode.valueOf(200));
		} catch (ArithmeticException e){
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", e.getMessage()));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(400));
		}*/
	}

	
	public int deletePolicyForUser(long c_id){
		MappingJacksonValue temp = getPolicyList(c_id, "id");
		System.out.println(temp.getValue());
		System.out.println(temp.getClass());
		System.out.println(temp.getSerializationView());
		System.out.println();
		return 0;
	}

	public int deletePolicy(long c_id, long p_id){
		System.out.println();
		return 0;
	}



	/**
	 * Setup Methode für das erzeugen von Testdaten
	 */
	void setUp(){
			LocalDate startDate = LocalDate.of(2017, 1, 15);
			LocalDate endDate1 = LocalDate.of(2099, 1, 1);
			LocalDate birthDate1 = LocalDate.of(2015, 1, 1);
			LocalDate birthDate2 = LocalDate.of(2015, 1, 2);
			ObjectOfInsuranceEntity cat1 = new ObjectOfInsuranceEntity("Belly", "Bengal", "Braun", birthDate1, false, "anhänglich", "drinnen", 4);
			ObjectOfInsuranceEntity cat2 = new ObjectOfInsuranceEntity("Rough", "Bengal", "Schwarz", birthDate2, false, "draufgängerisch", "drinnen", 4);
			PolicyEntity policy1 = new PolicyEntity(1 , startDate, endDate1, 50000, 765,cat1);
			PolicyEntity policy2 = new PolicyEntity(1 ,startDate, endDate1, 50000, 765 ,cat2);
			oRepository.save(cat1);
			oRepository.save(cat2);
			pRepository.save(policy1);
			pRepository.save(policy2);
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
			cRepository.saveAll(entities);
	}
	/**
	 * Diese Methode fragt den Customer beim Customer-Service ab
	 * @param c_id Customer-ID, des anzufragenden Customers
	 * @return CustomerRequest-Objekt, der die meisten Daten des Kunden enthält
	 */
	CustomerRequest getCustomerRequest(Long c_id) throws WebClientException, CustomerNotFoundException, IllegalArgumentException{
		if(c_id == null) throw new IllegalArgumentException();
		if(debugmode) System.out.println("getCustomerRequest: c_id: " + c_id);
		String customerURL = "http://" + customerUrl + ":8080/customer/{c_id}";
		if(debugmode) System.out.println("getCustomerRequest: customerURL: " + customerURL);
		WebClient customerClient = WebClient.create();
		WebClient.ResponseSpec responseSpec = customerClient.get().uri(customerURL,c_id).retrieve();
		CustomerRequest customer = responseSpec
			.onStatus(RetStatus -> HttpStatus.NOT_FOUND.equals(RetStatus), ex -> {throw new CustomerNotFoundException();})
			//.onStatus(RetStatus -> HttpStatus.NOT_FOUND.equals(RetStatus), ex -> Mono.empty())
			.bodyToMono(CustomerRequest.class)
			.block();
		if(debugmode) System.out.println("getCustomerRequest: customer: " + customer);
		return customer;
	}

	/**
	 * Diese Methode sendet eine Anfrage an den Mail
	 * @param mailUrl
	 * @param mail
	 * @return
	 */
	ResponseEntity<String> sendMail(String mailUrl, MailPolicyEntity mail) throws MailSendException{
		if(debugmode) System.out.println("sendMail: mailUrl: " + mailUrl + " mail: " + mail);
		String url = "http://" + notificationUrl + ":8080/"+ mailUrl;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, mail, String.class);
		if(response.getStatusCode() != HttpStatus.OK) throw new MailSendException();
		if(debugmode) System.out.println("sendMail: url: " + url + " response: " + response);

		/*
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
		} else {
			System.out.println("Request Failed");
		}
		*/
		if(debugmode) System.out.println("sendMail: response: " + response);
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
}
