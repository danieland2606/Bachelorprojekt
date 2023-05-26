package com.meowmed.rdabilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Diese Klassen ist die Java-Startklasse und ruft den BillingController auf
 * 
 * @author Mozamil Ahmadzaei, Alexander Hampel
 */
@SpringBootApplication
public class RdaBillingApplication {
	/**
	 * Main-Applikation
	 * @param args Argumente beim Programmstart 
	 */
	public static void main(String[] args) {
		SpringApplication.run(RdaBillingApplication.class, args);
	}

}
