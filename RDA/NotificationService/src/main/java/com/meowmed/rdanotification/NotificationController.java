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
 * @author Alexander Hampel, Mozamil Ahmadzaei, Daniel Arnold
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
     * @url POST "http://domain:port/customernotification"
     * @param details Ein Customer-Objekt, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */

    @PostMapping("/customernotification")
    public ResponseEntity<String> postNotificationCustomer(@RequestBody MailCustomerEntity details) {
        return nService.customerNotification(details);
    }

    /**
     * Diese Methode nimmt eine Vertrags-Post-Anfrage für den Email-Versand an und sendet eine vorbereitete EMail
     * @url POST "http://domain:port/policynotification"
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    @PostMapping("/policynotification")
    public ResponseEntity<String> postNotificationPolicy(@RequestBody MailPolicyEntity details) {
        return nService.policyNotification(details);
    }

    /**
     * Diese Methode nimmt eine Vertragsänderung-Post-Anfrage für den Email-Versand an und sendet eine vorbereitete EMail
     * @url POST "http://domain:port/policychangenotification"
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    @PostMapping("/policychangenotification")
    public ResponseEntity<String> putNotificationChangePolicy(@RequestBody MailPolicyEntity details) {
        return  nService.changePolicyNotification(details);
    }

    /**
     * Diese Methode nimmt eine Customeränderungs-Post-Anfrage für den Email-Versand an und sendet eine vorbereitete EMail
     * @url POST "http://domain:port/customerchangenotification"
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    @PostMapping("/customerchangenotification")
    public ResponseEntity<String> putNotificationChangeCustomer(@RequestBody MailCustomerEntity details) {
        return  nService.changeCustomerNotification(details);
    }

    /**
     * Diese Methode nimmt eine Vertragslöschung-Post-Anfrage für den Email-Versand an und sendet eine vorbereitete EMail
     * @url POST "http://domain:port/policydeletenotification"
     * @param details Ein Vertrags-Objekt mit zusätzlichen Infos, dessen Inhalt in der EMail verwendet wird
     * @return Zurück kommt ein HTTP-Statuscode, der aussagt, ob die Mail versendet wurde
     */
    @PostMapping("/policydeletenotification")
    public ResponseEntity<String> deleteNotificationPolicy(@RequestBody MailPolicyEntity details) {
        return nService.deleteNotificationPolicy(details);
    }

}
