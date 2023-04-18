package com.meowmed.rdanotification;

import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Diese Klasse ist der REST-Controller
 * 
 * @apiNote Die Schnittstelle ist in der Postman-Collection verzeichnet und steht in den Aufrufen
 * @author Alexander Hampel, Mozamil Ahmadzaei
 * 
 */

@RestController
public class NotificationController {

    private final NotificationService nService;
    /**
     * Diese Methode ist die Initialisierung des REST-Controllers
     * @param notificationService Dependency-Injektion von Spring Boot
     */
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.nService = notificationService;
    }

    /**
     * Diese Methode nimmt eine Customer-Post-Anfrage für den Email-Versand an und sendet eine vorbereitete EMail
     * @url "http://domain:port/customernotification"
     * @param details Ein Customer-Objekt, dessen Inhalt in der EMail verwendet wird
     * {
            "firstName": "Jan",
            "lastName": "Lorenz",
            "formofAdress": "Mr.",
            "martialStatus": "ledig",
            "dateOfBirth": "1999-11-03",
            "employmentStatus": "student",
            "address": {
                "city": "Hildesheim",
                "street": "Burgerking Hbf",
                "postalCode": 31137
            },
            "phoneNumber": "+49123456789",
            "email": "jan-niklas-johannes.lorenz@stud.hs-hannover.de",
            "bankDetails": "DE2131627312371351232"
        }
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */

    @PostMapping("/customernotification")
    public ResponseEntity<String> postNotificationCustomer(@RequestBody MailCustomerEntity details) {
        return nService.customerNotification(details);
    }

    /**
     * Diese Methode nimmt eine Vertrags-Post-Anfrage für den Email-Versand an und sendet eine vorbereitete EMail
     * @url "http://domain:port/customernotification"
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * {
            "firstName" : "Alexander",
            "lastName" : "Hampel",
            "formOfAdress": "Herr",
            "martialStatus" : "erledigt",
            "dateOfBirth" : "1996-01-14",
            "employmentStatus": "student",
            "phoneNumber": "0123456789",
            "email": "alexander.hampel@stud.hs-hannover.de",
            "bankDetails": "DE123456789123456789",
            "city": "Hannover",
            "street": "Ricklinger Stadtweg 120",
            "postalCode": "30149",
            "pid": 2,
            "cid": 1,
            "startDate": "2023-05-07",
            "endDate": "2023-05-08",
            "coverage": 10000,
            "premium": 975.0,
            "name": "Nello",
            "race": "Bengalisch",
            "color": "alpine-weiss",
            "age": "2023-08-07",
            "castrated": false,
            "personality": "verspielt",
            "environment": "freiläufer",
            "weight" : 4
        }
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    @PostMapping("/policynotification")
    public ResponseEntity<String> postNotificationPolicy(@RequestBody MailPolicyEntity details) {
        //System.out.println(details);
        return nService.policyNotification(details);
    }
}
