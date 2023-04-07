package com.meowmed.rdacustomer;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.meowmed.rdacustomer.entity.CustomerRequest;


@RestController
public class CustomerController{

    private final CustomerService cService;
    //Test

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.cService =  customerService;
    }

    /**
     * Diese Methode nimmt die GET-Anfrage für die CustomerListe an und gibt diese gefiltert zurueck.
     * @url "http://domain/:port/customer"
     * @param fields ist eine Liste von Komma-separierten Daten, welche aus Vorname, Nachname und Addresse bestehen koennen.
     * @return Gibt eine Liste von den Kunden zurueck, welche mit den gegebenen fields übereinstimmen
     * Beispiel: "customerList": [
     *     {
     *       "id": 0,
     *       "firstName": "John",
     *       "lastName": "Doe",
     *       "address": {
     *         "city": "Hannover",
     *         "street": "Berliner Allee 100",
     *         "postalCode": "30855"
     *       }
     *     },
     *     {
     *       "id": 1,
     *       "firstName": "Jane",
     *       "lastName": "Eyre",
     *       "address": {
     *         "city": "Hannover",
     *         "street": "Berliner Allee 101",
     *         "postalCode": "30855"
     *       }
     *     }
     *   ]
     */
    @GetMapping("/customer")
    public MappingJacksonValue getCustomerList(@RequestParam(value = "fields") String fields) {
        return cService.getCustomerList(fields);
    }

    /**
     * Diese Methode nimmt die GET-Anfrage für einen Kunden entgegen und gibt diesen dann zurück
     * @url "http://domain/:port/customer/%22Kunden-ID""
     * @param c_id ID des gesuchten Customers
     * @return Rueckgabe ist ein CustomerEntity von dem die ID angegeben wurde in diesem Format:
     * {
     *   "firstName": "John",
     *   "lastName": "Doe",
     *   "formOfAdress": "Mr",
     *   "title": "",
     *   "maritalStatus": "single",
     *   "dateOfBirth": "2000-12-30",
     *   "employmentStatus": "employed",
     *   "address": {
     *     "city": "Hannover",
     *     "street": "Berliner Allee 100",
     *     "postalCode": "30855"
     *   },
     *   "phoneNumber": "+4951177488375",
     *   "email": "example@aol.com",
     *   "bankDetails": "IE12BOFI90000112345678"
     * }
     *
     */
    @GetMapping("/customer/{c_id}")
    public MappingJacksonValue getCustomer(@PathVariable Long c_id) {
        return cService.getCustomer(c_id);

    }
    /**
     * Diese Methode speichert ein CustomerEntity
     * @url "http://domain/:port/customer/"
     * @param cRequest ist das zu speichernde Objekt
     * {
     *   "firstName": "John",
     *   "lastName": "Doe",
     *   "formOfAdress": "Mr",
     *   "title": "",
     *   "maritalStatus": "single",
     *   "dateOfBirth": "2000-12-30",
     *   "employmentStatus": "employed",
     *   "address": {
     *     "city": "Hannover",
     *     "street": "Berliner Allee 100",
     *     "postalCode": "30855"
     *   },
     *   "phoneNumber": "+4951177488375",
     *   "email": "example@aol.com",
     *   "bankDetails": "IE12BOFI90000112345678"
     * }
     *
     * @return Gibt die ID aus dem erstellten Objekt zurück
     */
    @PostMapping("/customer")
    public MappingJacksonValue postCustomer(@RequestBody CustomerRequest cRequest) {
        return cService.postCustomer(cRequest);

    }
}