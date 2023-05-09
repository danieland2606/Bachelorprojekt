package com.meowmed.rdacustomer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.meowmed.rdacustomer.database.AddressRepository;
import com.meowmed.rdacustomer.database.CustomerRepository;
import com.meowmed.rdacustomer.entity.AddressEntity;
import com.meowmed.rdacustomer.entity.CustomerEntity;
import com.meowmed.rdacustomer.entity.CustomerRequest;
import com.meowmed.rdacustomer.entity.MailCustomerEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.time.LocalDate;

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
        setUp();
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
        CustomerEntity customer= new CustomerEntity(cRequest.getFirstName(), cRequest.getLastName(), cRequest.getTitle(), cRequest.getFormOfAddress(), cRequest.getMaritalStatus(), cRequest.getDateOfBirth(), cRequest.getEmploymentStatus(), cRequest.getAddress(), cRequest.getPhoneNumber(),cRequest.getEmail(),cRequest.getBankDetails(),cRequest.isDogOwner());
        cRepository.save(customer);
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

    public MappingJacksonValue customerUpdate(Long c_id, CustomerRequest cRequest) {

        Optional<CustomerEntity> currentCustomer = cRepository.findById(c_id);
        if(currentCustomer.isEmpty()) throw new RuntimeException();

        cRequest.getAddress().setId(currentCustomer.get().getAddress().getId());


        // Erzeugen und ersetzen der Customer
        CustomerEntity customer= new CustomerEntity(cRequest.getFirstName(), cRequest.getLastName(), cRequest.getTitle(), cRequest.getFormOfAddress(), cRequest.getMaritalStatus(), cRequest.getDateOfBirth(), cRequest.getEmploymentStatus(), cRequest.getAddress(), cRequest.getPhoneNumber(),cRequest.getEmail(),cRequest.getBankDetails(),cRequest.isDogOwner());
        aRepository.save(cRequest.getAddress());
        customer.setId(c_id);
        customer = cRepository.save(customer);

        /*// Versand der Mail TEST
        MailPolicyEntity mail = new MailPolicyEntity(policy, customer);
        ResponseEntity<String> response = sendMail("policychangenotification", mail);
        if (response.getStatusCode() != HttpStatus.OK) throw new MailSendException();
		{
			MappingJacksonValue errWrapper = new MappingJacksonValue(Collections.singletonMap("error", "Es gab Probleme, die Mail zu versenden, Daten wurden aber gespeichert"));
			return new ResponseEntity<MappingJacksonValue>(errWrapper,HttpStatusCode.valueOf(400));
		}
        */
        // Verpacken und Filtern von der Ausgabe
        MappingJacksonValue wrapper = new MappingJacksonValue(customer);
        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("customerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"))
                .setFailOnUnknownId(false));

        return wrapper;
        //return new ResponseEntity<MappingJacksonValue>(wrapper,HttpStatusCode.valueOf(201));

    }

    void setUp(){
        LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
        AddressEntity adressJan= new AddressEntity("Hildesheim","Burgerking Hbf","31137");
        CustomerEntity Jan= new CustomerEntity("Jan", "Lorenz", "", "Herr","ledig",birthdayOfJan,"student",adressJan , "+49123456789" ,"jan-niklas-johannes.lorenz@stud.hs-hannover.de" ,"DE2131627312371351232", false  );

        LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
        AddressEntity adressDaniel= new AddressEntity("Hannover", "Subway Hbf", "12345");
        CustomerEntity Daniel= new CustomerEntity("Daniel", "Arnold","","Herr", "ledig", birthdayofDaniel, "student", adressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE", false);
    
        LocalDate birthdayOfAlex= LocalDate.of(1996,01,14);
        AddressEntity adressAlex= new AddressEntity("Hildesheim","Burgerking Hbf","31137");
        CustomerEntity Alex= new CustomerEntity("Alexander","Hampel","","Herr","ledig",birthdayOfAlex,"student", adressAlex, "+49123456789", "alexander.hampel@stud.hs-hannover.de", "DE2131627312371351232", false);


        aRepository.save(adressJan);
        aRepository.save(adressDaniel);
        aRepository.save(adressAlex);
    
        cRepository.save(Jan);
        cRepository.save(Daniel);
        cRepository.save(Alex);
    }
}

