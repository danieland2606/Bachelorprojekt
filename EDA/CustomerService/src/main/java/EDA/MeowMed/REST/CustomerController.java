package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import EDA.MeowMed.Persistence.Entity.Address;
import EDA.MeowMed.Persistence.Entity.Customer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
public class CustomerController {

    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public MappingJacksonValue getCustomerList() {
        LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
        Address addressJan= new Address("Hildesheim","Burgerking Hbf","31137");
        Customer Jan= new Customer(0,"Jan","Lorenz","ledig",birthdayOfJan,"student", addressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
        LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
        Address AddressDaniel= new Address(new Long(6),"Hannover", "Subway Hbf", "12345");
        Customer Daniel= new Customer(new Long(1),"Daniel", "Arnold", "ledig", birthdayofDaniel, "student", AddressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE");
        MappingJacksonValue customerListJSON = new MappingJacksonValue(List.of(Jan, Daniel));
        //MappingJacksonValue customerListJSON = new MappingJacksonValue(customerService.getCustomerList());

        customerListJSON.setFilters(new SimpleFilterProvider()
                .addFilter("SimpleFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id","firstName","lastName","address"))
                .setFailOnUnknownId(false));
        return customerListJSON;
    }
    @GetMapping("/customer/{c_id}")
    public MappingJacksonValue getCustomer(@PathVariable Long c_id) {
        MappingJacksonValue customerJSON = new MappingJacksonValue(customerService.getCustomer(c_id));

        customerJSON.setFilters(new SimpleFilterProvider()
                .addFilter("NoIdFilter", SimpleBeanPropertyFilter.serializeAllExcept("id","id"))
                .setFailOnUnknownId(false));
        return customerJSON;
    }
    @PostMapping("/customer")
    public MappingJacksonValue postCustomer(@RequestBody Customer customer) {
        MappingJacksonValue idJSON = new MappingJacksonValue(customerService.addCustomer(customer));
        return idJSON;
    }
}
