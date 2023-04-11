package com.meowmed.rdanotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Diese Klasse ist die Java-Startklasse und ruft den NotificationController auf
 * 
 * @apiNote Die Schnittstelle ist in der Postman-Collection verzeichnet und steht in den Aufrufen
 * @author Alexander Hampel, Mozamil Ahmadzaei
 */


@SpringBootApplication
public class NotificationApplication {
	/**
	 * Main-Applikation
	 * @param args Argumente beim Programmstart
	 */
	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}
