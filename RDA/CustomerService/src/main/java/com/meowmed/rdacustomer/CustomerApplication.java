package com.meowmed.rdacustomer;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import com.meowmed.rdacustomer.database.AddressRepository;
import com.meowmed.rdacustomer.database.CustomerRepository;
import com.meowmed.rdacustomer.entity.*;

/**
 * Diese Klasse ist die Rest-Schnittstelle
 *
 * @apiNote Die Schnittstelle ist definiert in der Datei MeowMed_REST_Interface_1.1.md im Root-Verzeichnis der Git-Repo
 * @author Daniel Arnold, Jan Lorenz
 */

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class CustomerApplication {

    /**
     * Main-Applikation
     * @param args Argumente beim Programmstart
     */
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    /**
     * Diese Methode erstellt Das JPA-Repository und speichert Beispieldaten
     * @return Speichern der Beispieldaten
     */
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, AddressRepository addressRepository) {
        return args -> {
            LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
            AddressEntity adressJan= new AddressEntity("Hildesheim","Burgerking Hbf",31137);
            CustomerEntity Jan= new CustomerEntity("Jan","Lorenz","ledig",birthdayOfJan,"student", adressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");

            LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
            AddressEntity adressDaniel= new AddressEntity("Hannover", "Subway Hbf", 12345);
            CustomerEntity Daniel= new CustomerEntity("Daniel", "Arnold", "ledig", birthdayofDaniel, "student", adressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE");
        
            addressRepository.save(adressJan);
            addressRepository.save(adressDaniel);
        
            customerRepository.save(Jan);
            customerRepository.save(Daniel);
        };


    }



    //LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
    //        Adress adressJan= new Adress("Hildesheim","Burgerking Hbf","31137");
    //        CustomerEntity Jan= new CustomerEntity(0,"Jan","Lorenz","ledig",birthdayOfJan,"student", adressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
    //        LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
    //        Adress adressDaniel= new Adress("Hannover", "Subway Hbf", "12345");
    //        CustomerEntity Daniel= new CustomerEntity(1,"Daniel", "Arnold", "ledig", birthdayofDaniel, "student", adressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE");
    //        return List.of(Jan, Daniel);
    //LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
    //        Adress adressJan= new Adress("Hildesheim","Burgerking Hbf","31137");
    //        return new CustomerEntity(c_id,"Jan","Lorenz","ledig",birthdayOfJan,"student", adressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
    //return ThreadLocalRandom.current().nextInt(10000);
    //
    //
    //
    //
}
