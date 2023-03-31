package EDA.MeowMed.REST;

import EDA.MeowMed.Application.CustomerService;
import EDA.MeowMed.Persistence.Entity.Address;
import EDA.MeowMed.Persistence.Entity.Customer;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public MappingJacksonValue getCustomerList() {
        LocalDate birthdayOfJan= LocalDate.of(1999,11,03);
        Address addressJan= new Address(4L,"Hildesheim","Burgerking Hbf","31137");
        Customer Jan= new Customer(0L,"Jan","Lorenz","ledig",birthdayOfJan,"student", addressJan, "+49123456789", "jan-niklas-johannes.lorenz@stud.hs-hannover.de", "DE2131627312371351232");
        LocalDate birthdayofDaniel= LocalDate.of(2002,06,26);
        Address AddressDaniel= new Address(6L,"Hannover", "Subway Hbf", "12345");
        Customer Daniel= new Customer(1L,"Daniel", "Arnold", "ledig", birthdayofDaniel, "student", AddressDaniel, "+4942069123123", "daniel.arnold@stud.hs-hannover.de", "DE");
        MappingJacksonValue customerListJSON = new MappingJacksonValue(List.of(Jan, Daniel));
        //MappingJacksonValue customerListJSON = new MappingJacksonValue(customerService.getCustomerList());

        String filter = "id,firstName,lastName,address";
        Set<String> filterSet = new HashSet<>();
        filterSet.addAll(Arrays.asList(filter.split(",")));
        customerListJSON.setFilters(new SimpleFilterProvider()
                .addFilter("Customer", SimpleBeanPropertyFilter.filterOutAllExcept(filterSet))
                .addFilter("Address", SimpleBeanPropertyFilter.serializeAllExcept("id"))
                .setFailOnUnknownId(false));
        return customerListJSON;
    }
    @GetMapping("/customer/{c_id}")
    public MappingJacksonValue getCustomer(@PathVariable Long c_id) {
        MappingJacksonValue customerJSON = new MappingJacksonValue(customerService.getCustomer(c_id));

        customerJSON.setFilters(new SimpleFilterProvider()
                .addFilter("Customer", SimpleBeanPropertyFilter.serializeAllExcept("id"))
                .addFilter("Address", SimpleBeanPropertyFilter.serializeAllExcept("id"))
                .setFailOnUnknownId(false));
        return customerJSON;
    }
    @PostMapping("/customer")
    public MappingJacksonValue postCustomer(@RequestBody Customer customer) {
        MappingJacksonValue idJSON = new MappingJacksonValue(customerService.addCustomer(customer));
        return idJSON;
    }
}
