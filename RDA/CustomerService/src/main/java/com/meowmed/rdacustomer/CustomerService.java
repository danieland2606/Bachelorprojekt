package com.meowmed.rdacustomer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdacustomer.database.AddressRepository;
import com.meowmed.rdacustomer.database.CustomerRepository;
import com.meowmed.rdacustomer.entity.CustomerEntity;
import com.meowmed.rdacustomer.entity.CustomerRequest;
import com.meowmed.rdacustomer.entity.MailCustomerEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

/**
 * Diese Klasse ist die Service-Klasse des REST-Controllers
 *
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis des Git-Repos
 * @author Daniel Arnold, Jan Lorenz
 */
@Service
public class CustomerService {

    @Value("${docker.notificationurl}")
    private String notificationUrl;


    private final CustomerRepository cRepository;
    private final AddressRepository aRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository adressRepository) {
        this.cRepository = customerRepository;
        this.aRepository = adressRepository;
    }

    /** TODO
     * Diese Methode nimmt eine ID eines Customers entgegen und gibt ein CustomerEntity zurück
     * @param id ID des gefragten Customers
     * @return CustomerEntity ohne seine ID
     */
    public MappingJacksonValue getCustomer(long id){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findById(id));

        wrapper.setFilters(new SimpleFilterProvider()
        .addFilter("customerFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"))
        .setFailOnUnknownId(false));
        return wrapper;
    }

    /** TODO
     * Diese Methode nimmt einen String entgegen welcher aus Vorname, Nachname und Addresse bestehen kann.
     * @param fields String aus Vorname, Nachname und Addresse. Attribute sind mit "," getrennt.
     * @return Gibt eine Liste mit CustomerObjekten zurück, welche die gleichen Attribute aus fields haben.
     */
    //?fields=id,firstName,lastName,address
    public MappingJacksonValue getCustomerList(String fields){
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.findAll());
        List<String> customerList = new ArrayList<String>();

        /*  Warum ist das hier????
        MailCustomerEntity mail = new MailCustomerEntity();
        String notificationURL = "http://" + notificationUrl + ":8080";
        WebClient notificationClient = WebClient.create(notificationURL);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = notificationClient.method(HttpMethod.POST);
        //WebClient.ResponseSpec responseSpec = notificationClient.get().uri(customerURL,c_id).retrieve();

        Mono<String> result = notificationClient.post()
                .uri("/policynotification")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mail)
                .retrieve()
                .bodyToMono(String.class);
        */

        customerList.addAll(Arrays.asList(fields.split(",")));
        customerList.add("id");
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(customerList)))
                //.addFilter("addressFilter", SimpleBeanPropertyFilter.filterOutAllExcept(Set.copyOf(addressList)))
                .addFilter("addressFilter", SimpleBeanPropertyFilter.serializeAll())
                .setFailOnUnknownId(false));
        return wrapper;
    }

    /**
     * Diese Methode speichert ein CustomerEntity.
     * @param cRequest ist das zu speichernde Customer Objekt
     * @return Die ID des erstellten Objekts.
     */
    public MappingJacksonValue postCustomer(CustomerRequest cRequest) {
        aRepository.save(cRequest.getAddress());
        CustomerEntity customer= new CustomerEntity(cRequest.getFirstName(), cRequest.getLastName(), cRequest.getTitle(), cRequest.getFormOfAdress(), cRequest.getMaritalStatus(), cRequest.getDateOfBirth(), cRequest.getEmploymentStatus(), cRequest.getAddress(), cRequest.getPhoneNumber(),cRequest.getEmail(),cRequest.getBankDetails());
        
        MailCustomerEntity mail = new MailCustomerEntity(cRequest);
		System.out.println(mail);
		//WebClient notificationClient = WebClient.create("http://" + notificationUrl + ":8080");
		String url = "http://" + notificationUrl + ":8080/customernotification";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, mail, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful");
		} else {
			System.out.println("Request Failed");
		}
		
        
        MappingJacksonValue wrapper = new MappingJacksonValue(cRepository.save(customer));
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                .setFailOnUnknownId(false));
        return wrapper;
    }
}

