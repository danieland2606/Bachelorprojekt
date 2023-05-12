package com.meowmed.rdapolicy.module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.meowmed.rdapolicy.exceptions.MailSendException;
import com.meowmed.rdapolicy.persistence.entity.MailPolicyEntity;

public class PolicyMail {
    
	//Variable, initialisiert durch application.properties
	@Value("${docker.notificationurl}")
	private static String notificationUrl;

    @Value("${docker.debugmode}")
	private static boolean debugmode;
    
    /**
	 * Diese Methode sendet eine Anfrage an den Mail
	 * @param mailUrl
	 * @param mail
	 * @return
	 */
	public static ResponseEntity<String> sendMail(String mailUrl, MailPolicyEntity mail) throws MailSendException{
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
