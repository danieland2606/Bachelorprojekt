package EDA.MeowMed;

import EDA.MeowMed.Entities.Address;
import EDA.MeowMed.Entities.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RestController
public class MeowMedApplication {

	static final String topicExchangeName = null;
	static final String queueName = null;

	public static void main(String[] args) {
		SpringApplication.run(MeowMedApplication.class, args);
	}
	@GetMapping("/customer")
	public List<Customer> getCustomerList() {
		LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
		Address addressJan= new Address("Hildesheim","Burgerking Hbf","31137");
		Customer Jan= new Customer(0,"Jan","Lorenz","ledig",birthdayOfJan,"student", addressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
		LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
		Address AddressDaniel= new Address("Hannover", "Subway Hbf", "12345");
		Customer Daniel= new Customer(1,"Daniel", "Arnold", "ledig", birthdayofDaniel, "student", AddressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE");
		return List.of(Jan, Daniel);
	}
	@GetMapping("/customer/{c_id}")
	public Customer getCustomer(@PathVariable Long c_id) {
		LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
		Address AddressJan= new Address("Hildesheim","Burgerking Hbf","31137");
		return new Customer(c_id,"Jan","Lorenz","ledig",birthdayOfJan,"student", AddressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
	}
	@PostMapping("/customer") //ID idk
	public int postCustomer() {
		return ThreadLocalRandom.current().nextInt(10000);
	}
}