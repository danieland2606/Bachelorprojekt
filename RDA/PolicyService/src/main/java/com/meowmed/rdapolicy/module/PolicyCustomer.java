package com.meowmed.rdapolicy.module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import com.meowmed.rdapolicy.exceptions.CustomerNotFoundException;
import com.meowmed.rdapolicy.persistence.entity.CustomerRequest;

public class PolicyCustomer {
    //Variable, initialisiert durch application.properties
	@Value("${docker.customerurl}")
	private static String customerUrl;

    @Value("${docker.debugmode}")
	private static boolean debugmode;
	//if(debugmode) System.out.println("getPolicyList:");

	/**
	 * Diese Methode fragt den Customer beim Customer-Service ab
	 * @param c_id Customer-ID, des anzufragenden Customers
	 * @return CustomerRequest-Objekt, der die meisten Daten des Kunden enthält
	 */
	public static CustomerRequest getCustomerRequest(Long c_id) throws NestedRuntimeException{
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

	
}
