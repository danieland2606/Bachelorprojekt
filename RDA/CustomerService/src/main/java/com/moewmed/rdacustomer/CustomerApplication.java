package com.moewmed.rdacustomer;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import com.moewmed.rdacustomer.database.AddressRepository;
import com.moewmed.rdacustomer.database.CustomerRepository;
import com.moewmed.rdacustomer.entity.*;



@SpringBootApplication
@RestController
@RequestMapping("/api")
public class CustomerApplication {

    CustomerService cService= new CustomerService();
    CustomerRepository cRepository;
    AddressRepository aRepository;


    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
    @GetMapping("/customer")
    public MappingJacksonValue getCustomerList(@RequestParam(value = "fields") String fields) {
        return cService.getCustomerList(fields, cRepository);
    }
    @GetMapping("/customer/{c_id}")
    public MappingJacksonValue getCustomer(@PathVariable Long c_id) {
        return cService.getCustomer(c_id,cRepository);

    }
    @PostMapping("/customer") //ID idk
    public MappingJacksonValue postCustomer(@RequestBody CustomerRequest cRequest) {
        return cService.postCustomer(cRequest, cRepository);

    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, AddressRepository addressRepository) {
        cRepository = customerRepository;
        aRepository = addressRepository;
        return args -> {
            LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
            AddressEntity adressJan= new AddressEntity("Hildesheim","Burgerking Hbf","31137");
            CustomerEntity Jan= new CustomerEntity("Jan","Lorenz","ledig",birthdayOfJan,"student", adressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");

            LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
            AddressEntity adressDaniel= new AddressEntity("Hannover", "Subway Hbf", "12345");
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
