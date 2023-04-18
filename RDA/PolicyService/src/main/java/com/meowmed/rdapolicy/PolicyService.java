package com.meowmed.rdapolicy;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.meowmed.rdapolicy.database.CatRepository;
import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.CatEntity;
import com.meowmed.rdapolicy.entity.CustomerRequest;
import com.meowmed.rdapolicy.entity.MailPolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;


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

	private final PolicyRepository pRepository;
	private final ObjectOfInsuranceRepository oRepository;
	private final CatRepository cRepository;

	@Autowired
	public PolicyService(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository, CatRepository catRepository) {
		this.pRepository = policyRepository;
		this.oRepository = objectOfInsuranceRepository;
		this.cRepository = catRepository;
	}

    /**
	 * Diese Methode nimmt die Anfrage des REST-Controllers für die PolicyListe an und gibt diese gefiltert zurück.
	 * @param c_id ID des Customers dessen Policys angefragt werden. 
	 * @param fields Eine Liste an Komma-separierten an benötigten Feldern (z.B. startDate,endDate,coverage,objectOfInsurance.name)
	 * @return Zurück kommt eine gefilterte Liste an PolicyEntitys, die ähnlich dem Beispiel aussieht:
	 */
    public MappingJacksonValue getPolicyList(Long c_id, String fields) {
		MappingJacksonValue wrapper = new MappingJacksonValue(pRepository.findByCid(c_id));
		
		List<String> policyList = new ArrayList<String>();
		policyList.addAll(Arrays.asList(fields.split(",")));
		List<String> ooIList = new ArrayList<String>();
		List<String> removeList = new ArrayList<String>();

		boolean containsOoI = false;
		for (String result : policyList) {
			if(result.contains("objectOfInsurance.")){
				ooIList.add(result.substring(18));
				removeList.add(result);
				//policyList.remove(result);
				containsOoI = true;
				//if(!policyList.contains("objectOfInsurance")){
				//	policyList.add("objectOfInsurance");
				//}
			}
		}
		policyList.removeAll(removeList);

		if(containsOoI) policyList.add("objectOfInsurance");
		policyList.add("id");
		
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(policyList)))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(ooIList)))
		.setFailOnUnknownId(false));
		return wrapper;
	}

	/**
	 * Diese Methode nimmt die Anfrage des RESTControllers an eine Policy von einem Kunden an und gibt diese gefiltert zurück
	 * @param c_id ID des Customers dessen Policys angefragt werden.
	 * @param p_id ID der Policy des Kunden.
	 * @return Zurückkommt ein PolicyEntity, bei der die Policy- und Customer-ID herausgefiltert wird
	 */
    public MappingJacksonValue getPolicy(Long c_id, Long p_id){
		MappingJacksonValue wrapper = new MappingJacksonValue(pRepository.findById(p_id));
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.serializeAllExcept("id", "c_id"))
		.addFilter("objectOfInsuranceFilter", SimpleBeanPropertyFilter.serializeAll())
		.setFailOnUnknownId(false));
		
		return wrapper;
	}

	/**
	 * Diese Methode speichert ein PolicyEntity. PostalCode vom Customer wird beim CustomerService angefragt.
	 * @param c_id ID des Customers bei dem die Policys gespeichert wird.
	 * @param pRequest Das zu speichernde Objekt
	 * @return Die ID der gerade erstellten Objekts
	 * 
	 */
    public MappingJacksonValue postPolicy(Long c_id, PolicyRequest pRequest){
		oRepository.save(pRequest.getObjectOfInsurance());
		String customerURL = "http://" + customerUrl + ":8080/customer/{c_id}";
		System.out.println(customerURL);
		WebClient customerClient = WebClient.create();
		WebClient.ResponseSpec responseSpec = customerClient.get().uri(customerURL,c_id).retrieve();
		CustomerRequest customer = responseSpec.bodyToMono(CustomerRequest.class).block();
		System.out.println(customer);

		PriceCalculationEntity tempCalc = new PriceCalculationEntity(customer.getAddress().getPostalCode(), pRequest.getCoverage(), pRequest.getObjectOfInsurance().getRace(), 
				pRequest.getObjectOfInsurance().getColor(), pRequest.getObjectOfInsurance().getDateOfBirth(), pRequest.getObjectOfInsurance().isCastrated(), 
				pRequest.getObjectOfInsurance().getPersonality(), pRequest.getObjectOfInsurance().getEnvironment(), pRequest.getObjectOfInsurance().getWeight());

		PolicyEntity policy = new PolicyEntity(c_id, pRequest.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), getPolicyPrice(tempCalc), pRequest.getObjectOfInsurance());
		policy = pRepository.save(policy);

		MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
		System.out.println(mail);
		//WebClient notificationClient = WebClient.create("http://" + notificationUrl + ":8080");
		String url = "http://" + notificationUrl + ":8080/policynotification";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, mail, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
		} else {
			System.out.println("Request Failed");
		}
		
		/*
		Mono<ResponseEntity<String>> result = WebClient.create().post().uri("http://notification:8080/policynotification")
											//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
											.contentType(MediaType.APPLICATION_JSON)
											.bodyValue(mail)
											.retrieve()
											.toEntity(String.class);
		System.out.println(result.cast(ResponseEntity.class).toString());
		System.out.println(mail);
		*/
		MappingJacksonValue wrapper = new MappingJacksonValue(policy);
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));

		return wrapper;
	}

	/**
	 * Hier wird der Preis berechnet für monatlichen Kosten
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Zurück kommt ein Wert als double für die monatlichen Kosten
	 */
    public double getPolicyPrice(PriceCalculationEntity body){
		System.out.println(body);
		
		//PriceCalculationEntity body
		CatEntity cat = cRepository.findByRace(body.getRace());
		if (cat==null) return 0;
		double grundpreis = 0;
		double endbetrag = 0;
		// Die Jahresdeckung dient als Startwert

		// Ist die Katze Schwarz, ist die Versicherung Teurer
		switch(body.getColor()){
			case "schwarz":
				grundpreis = 0.02*body.getCoverage();
				break;
			default:
				grundpreis = 0.015*body.getCoverage();
		}

		//System.out.println("Grundpreis: " + grundpreis + " Endbetrag: " + endbetrag);
		//  Der aktuelle Preis startet als Grundpreis. Später wird der Preis um Prozentteile des Grundpreises erhöht
		endbetrag = grundpreis;
		// Erhöhe die Kosten um 5€ für jedes Kilo abweichung vom Durchschnittsgewichtes der Rasse
		endbetrag += Math.abs((cat.getUpperAverageWeight()-body.getWeight())*5);
		// Erhöhe die Kosten um 1% des Grundpreises für jeden Punkt in der Krankheitsanfälligkeitsskala
		endbetrag += grundpreis*(cat.getIllnessFactor()*0.01);

		//System.out.println("Grundpreis: " + grundpreis + " Endbetrag: " + endbetrag);

		// Ist es eine Draußenkatze, steigere den Preis um 1% des Grundwertes
		if(body.getEnvironment() == "draussen") endbetrag += 0.01*grundpreis;
		// Falls die Katze im oberen Quantil des Durchschnittsalters oder älter ist, erhöhe den Preis um 20% des Grundpreises
		// Ist die Katze Jung, erhöhe den Preis um 5€
		if(ChronoUnit.YEARS.between(body.getDateOfBirth(), LocalDate.now())>(cat.getUpperAverageAge()*0.75)){
			endbetrag += 0.2*grundpreis;
		} else if (ChronoUnit.YEARS.between(body.getDateOfBirth(), LocalDate.now())<=2){
			endbetrag+=5;
		};

		//System.out.println("Grundpreis: " + grundpreis + " Endbetrag: " + endbetrag);

		// Ist die Katze kastriert, erhöhe den Preis um 5€
		if(body.isCastrated()) endbetrag+=5;
		// Hat der Besitzer eine PLZ die mit 0 oder 1 startet, erhöhe den Preis um 5% des Grundpreises
		if(body.getPostalCode()<20000) endbetrag+= 0.05*grundpreis;

		// Auf 2 Nachkommastellen runden
		endbetrag = ((int)(endbetrag*100))/100;
		//System.out.println("Grundpreis: " + grundpreis + " Endbetrag: " + endbetrag);

		return endbetrag;
	}

	/**
	 * Die Wert für die monatliche Kosten werden in ein JSONObjekt umgewandelt
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Ein zu JSON umwandelbarer Wert
	*/
	public ResponseEntity<String> getPolicyPriceRequest(String details){
		ObjectMapper mapper = new ObjectMapper();
		String str = new String(Base64.getUrlDecoder().decode(details), Charset.forName("UTF-8"));
		PriceCalculationEntity body = null;
		try {
			mapper.registerModule(new JavaTimeModule());
			body = mapper.readValue(str,PriceCalculationEntity.class);
			if (body==null){ 
				return new ResponseEntity<String>("Convert was not Possible",HttpStatusCode.valueOf(500));	
			}
			return new ResponseEntity<String>(mapper.writeValueAsString(Collections.singletonMap("premium", getPolicyPrice(body))),HttpStatusCode.valueOf(200));
		} catch (JsonMappingException e) {
			System.out.println(e);
		} catch (JsonProcessingException e){
			System.out.println(e);
		}
		return new ResponseEntity<String>("Convert was not Possible",HttpStatusCode.valueOf(500));
	}
}
