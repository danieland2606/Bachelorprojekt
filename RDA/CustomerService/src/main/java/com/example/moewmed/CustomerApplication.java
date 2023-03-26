package com.example.moewmed;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.moewmed.entity.*;


@SpringBootApplication
@RestController
@RequestMapping("/api")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
    @GetMapping("/customer")
    public List<CustomerEntity> getCustomerList() {
        LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
        Adress adressJan= new Adress("Hildesheim","Burgerking Hbf","31137");
        CustomerEntity Jan= new CustomerEntity(0,"Jan","Lorenz","ledig",birthdayOfJan,"student", adressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
        LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
        Adress adressDaniel= new Adress("Hannover", "Subway Hbf", "12345");
        CustomerEntity Daniel= new CustomerEntity(1,"Daniel", "Arnold", "ledig", birthdayofDaniel, "student", adressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE");
        return List.of(Jan, Daniel);
    }
    @GetMapping("/customer/{c_id}")
    public CustomerEntity getCustomer(@PathVariable Long c_id) {
        LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
        Adress adressJan= new Adress("Hildesheim","Burgerking Hbf","31137");
        return new CustomerEntity(c_id,"Jan","Lorenz","ledig",birthdayOfJan,"student", adressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
    }
    @PostMapping("/customer") //ID idk
    public int postCustomer() {
        return ThreadLocalRandom.current().nextInt(10000);
    }

}