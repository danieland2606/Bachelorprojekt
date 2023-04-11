package com.meowmed.rdapolicy;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdapolicy.database.ObjectOfInsuranceRepository;
import com.meowmed.rdapolicy.database.PolicyRepository;
import com.meowmed.rdapolicy.entity.CustomerRequest;
import com.meowmed.rdapolicy.entity.MailPolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyEntity;
import com.meowmed.rdapolicy.entity.PolicyRequest;
import com.meowmed.rdapolicy.entity.PriceCalculationEntity;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
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

	private final PolicyRepository pRepository;
	private final ObjectOfInsuranceRepository oRepository;

	@Autowired
	public PolicyService(PolicyRepository policyRepository, ObjectOfInsuranceRepository objectOfInsuranceRepository) {
		this.pRepository = policyRepository;
		this.oRepository = objectOfInsuranceRepository;
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
		WebClient.ResponseSpec responseSpec2 = customerClient.get().uri(customerURL,c_id).retrieve();
		CustomerRequest customer = responseSpec2.bodyToMono(CustomerRequest.class).block();
		System.out.println(customer);

		PriceCalculationEntity tempCalc = new PriceCalculationEntity(customer.getAddress().getPostalCode(), pRequest.getCoverage(), pRequest.getObjectOfInsurance().getRace(), 
				pRequest.getObjectOfInsurance().getColor(), pRequest.getObjectOfInsurance().getAge(), pRequest.getObjectOfInsurance().isCastrated(), 
				pRequest.getObjectOfInsurance().getPersonality(), pRequest.getObjectOfInsurance().getEnviroment(), pRequest.getObjectOfInsurance().getWeight());

		PolicyEntity policy = new PolicyEntity(c_id, pRequest.getStartDate(), pRequest.getEndDate(), pRequest.getCoverage(), getPolicyPrice(tempCalc), pRequest.getObjectOfInsurance());
		
		MappingJacksonValue wrapper = new MappingJacksonValue(pRepository.save(policy));
		wrapper.setFilters(new SimpleFilterProvider()
		.addFilter("policyFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
		.setFailOnUnknownId(false));

		MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
		String notificationURL = "http://" + notificationUrl + ":8080";
		System.out.println(notificationURL);
		WebClient client = WebClient.create(notificationURL);
		UriSpec<RequestBodySpec> uriSpec = client.post();
		RequestBodySpec bodySpec = uriSpec.uri("/policynotification");
		RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("data");
		ResponseSpec responseSpec = headersSpec.header(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		  .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
		  .acceptCharset(StandardCharsets.UTF_8)
		  .ifNoneMatch("*")
		  .ifModifiedSince(ZonedDateTime.now())
		  .retrieve();
		  Mono<String> response = headersSpec.retrieve()
		  .bodyToMono(String.class);
		  System.out.println(response);
		//WebClient postClient = WebClient.create(notificationURL);
		//WebClient.ResponseSpec postResponseSpec = postClient.post().uri("/policynotification").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(mail), MailPolicyEntity.class).retrieve();
		//System.out.println(postResponseSpec);
		/*/
					customerClient.post()
					.uri(notificationURL)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					//.contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(mail), MailPolicyEntity.class)
					//.bodyValue(mail)
					.exchange();
					//.bodyToMono(ResponseEntity<String>.class);
					//.toEntity(String.class);
		//System.out.println(result.cast(ResponseEntity.class).toString());
		*/
		return wrapper;
	}

	/**
	 * Hier wird der Preis berechnet für monatlichen Kosten
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Zurück kommt ein Wert als double für die monatlichen Kosten
	 */
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

	/**
	 * Die Wert für die monatliche Kosten werden in ein JSONObjekt umgewandelt
	 * @param body Übergeben werden die Parameter als PriceCalculationEntity
	 * @return Ein zu JSON umwandelbarer Wert
	*/
	public Map<String,Double> getPolicyPriceRequest(PriceCalculationEntity body){
		return Collections.singletonMap("premium", getPolicyPrice(body));
	}
}
